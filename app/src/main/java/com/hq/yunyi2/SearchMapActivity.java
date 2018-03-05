package com.hq.yunyi2;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;


import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;


public class SearchMapActivity extends Activity {
    private TextureMapView mapView;
    private BaiduMap bdMap;

    private PoiSearch poiSearch;
    private SuggestionSearch suggestionSearch;

    private AutoCompleteTextView editSearchKeyEt;
    private ArrayAdapter<String> sugAdapter = null;

    private LocationClient locationClient;
    private BDLocationListener locationListener;

    //当前位置的参数
    private double longitude;// 精度
    private double latitude;// 维度
    private float radius;// 定位精度半径，单位是米
    private String province;// 省份信息
    private String city;// 城市信息
    private String district;// 区县信息
    private float direction;// 手机方向信息
    private String address_detail, address_general;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);

        init();
    }

    private void init() {
        mapView = (TextureMapView) findViewById(R.id.mapview);
        //不显示logo
        View child = mapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        //不显示加减按钮
        mapView.showZoomControls(false);
        //不显示比例尺
        mapView.showScaleControl(false);
        bdMap = mapView.getMap();

        editSearchKeyEt = (AutoCompleteTextView) findViewById(R.id.searchkey);

        // 实例化PoiSearch对象
        poiSearch = PoiSearch.newInstance();
        // 设置检索监听器
        poiSearch.setOnGetPoiSearchResultListener(poiSearchListener);
        // 实例化suggestionSearch对象
        suggestionSearch = SuggestionSearch.newInstance();
        // 设置检索监听器
        suggestionSearch.setOnGetSuggestionResultListener(suggestionResultListener);

        editSearchKeyEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int start, int before,
                                      int count) {
                new getData().execute(cs.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence cs, int start,
                                          int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable cs) {
            }
        });

        // 定位到当前位置
        //开启地位图层
        bdMap.setMyLocationEnabled(true);
        // 1. 初始化LocationClient类
        locationClient = new LocationClient(getApplicationContext());
        // 2. 声明LocationListener类
        locationListener = new MyLocationListener();
        // 3. 注册监听函数
        locationClient.registerLocationListener(locationListener);
        // 4. 设置参数
        LocationClientOption locOption = new LocationClientOption();
        locOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        locOption.setCoorType("bd09ll");// 设置定位结果类型
        locOption.setScanSpan(5000);// 设置发起定位请求的间隔时间,ms
        locOption.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        locOption.setNeedDeviceDirect(true);// 设置返回结果包含手机的方向

        locationClient.setLocOption(locOption);
        // 5. 注册位置提醒监听事件

        bdMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                setMarker(latLng);
                if (locationClient.isStarted()) {
                    locationClient.stop();
                }

                GeoCoder geoCoder = GeoCoder.newInstance();
                ReverseGeoCodeOption op = new ReverseGeoCodeOption();
                op.location(latLng);
                geoCoder.reverseGeoCode(op);
                geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                    @Override
                    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

                    }

                    @Override
                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                        address_general = reverseGeoCodeResult.getAddress();
                        address_detail = reverseGeoCodeResult.getSematicDescription();
                    }
                });
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });


        // 6. 开启/关闭 定位SDK
        locationClient.start();
        //实时定位10秒后自动关闭
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (locationClient.isStarted()) {
                    locationClient.stop();
                }
            }
        }, 5000);
    }

    //底部按钮
    public void btn_getinfo(View v) {
        //Toast.makeText(SearchMapActivity.this, "address-->>"+address, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SearchMapActivity.this, CreateAncestorInfoActivity.class);
        intent.putExtra("address", address_general+address_detail);
        setResult(1,intent);
        finish();
    }

    //悬浮定位图标点击事件
    public void float_button(View v) {
        locationClient.start();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (locationClient.isStarted()) {
                    locationClient.stop();
                }
                // Toast.makeText(this,"已更新当前位置!",Toast.LENGTH_SHORT).show();
            }
        }, 5000);
    }

    //退出箭头点击事件
    public void exit_before(View v) {
        Intent intent = new Intent(SearchMapActivity.this, CreateAncestorInfoActivity.class);
        setResult(2,intent);
        finish();
    }

    // 搜索按钮点击事件
    public void searchButtonProcess(View v) {
        String city_string;
        if (TextUtils.isEmpty(city)) {
            city_string = "武汉";
        } else {
            city_string = city;
        }
        String key_string = editSearchKeyEt.getText().toString();
        if (city_string != null && key_string != null) {
            if (locationClient.isStarted()) {
                locationClient.stop();
            }
            poiSearch.searchInCity((new PoiCitySearchOption()).city(
                    city_string).keyword(key_string));

        } else {
            Toast.makeText(SearchMapActivity.this, "请输入完整信息后再搜索", Toast.LENGTH_SHORT).show();
        }
    }


    OnGetSuggestionResultListener suggestionResultListener = new OnGetSuggestionResultListener() {

        @Override
        public void onGetSuggestionResult(SuggestionResult suggestionResult) {
            // TODO Auto-generated method stub
            if (suggestionResult == null
                    || suggestionResult.getAllSuggestions() == null) {
                return;
            }
            sugAdapter.clear();
            for (SuggestionResult.SuggestionInfo info : suggestionResult
                    .getAllSuggestions()) {
                if (info.key != null) {
                    sugAdapter.add(info.key);
                }
            }
            sugAdapter.notifyDataSetChanged();
        }

    };

    OnGetPoiSearchResultListener poiSearchListener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if (poiResult == null
                    || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
                Toast.makeText(SearchMapActivity.this, "未找到结果",
                        Toast.LENGTH_LONG).show();
                // return;
            }

            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
                bdMap.clear();

                LatLng point = poiResult.getAllPoi().get(0).location;
                getLocationAddress(point);
                setNowLocation(point);
                setMarker(point);
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
            if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(SearchMapActivity.this, "抱歉，未找到结果",
                        Toast.LENGTH_SHORT).show();
            } else {// 正常返回结果的时候，此处可以获得很多相关信息
                Toast.makeText(
                        SearchMapActivity.this,
                        poiDetailResult.getName() + ": "
                                + poiDetailResult.getAddress(),
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult arg0) {
            // TODO Auto-generated method stub

        }
    };

    // 设置地图到指定位置
    public void setNowLocation(LatLng point) {
        MapStatus mapStatus = new MapStatus.Builder().target(point).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
                .newMapStatus(mapStatus);
        // bdMap.setMapStatus(mMapStatusUpdate);
        bdMap.animateMapStatus(mMapStatusUpdate);
    }

    // 清除地图上的图标，在指定位置添加一个图标
    public void setMarker(LatLng point) {
        // 构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.location);
        // 构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(point)
                .icon(bitmap);
        // 在地图上添加Marker，并显示
        bdMap.clear();
        bdMap.addOverlay(option);
    }

    // 通过经纬度获取地址,目前没有用，
    private void getLocationAddress(LatLng point) {

        GeoCoder geoCoder = GeoCoder.newInstance();
        // 设置反地理经纬度坐标,请求位置时,需要一个经纬度
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(point));
        // 设置地址或经纬度反编译后的监听,这里有两个回调方法,
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(
                    ReverseGeoCodeResult reverseGeoCodeResult) {

                if (reverseGeoCodeResult == null
                        || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    return;
                }

            }

        });

    }

    class MyLocationListener implements BDLocationListener {

        // 异步返回的定位结果
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return;
            }

            longitude = location.getLongitude();
            latitude = location.getLatitude();
            if (location.hasRadius()) {// 判断是否有定位精度半径
                radius = location.getRadius();
            }

            direction = location.getDirection();// 获取手机方向，【0~360°】,手机上面正面朝北为0°
            province = location.getProvince();// 省份
            city = location.getCity();// 城市
            district = location.getDistrict();// 区县
            //Toast.makeText(PoiSearchActivity.this,
            //        province + "~" + city + "~" + district, Toast.LENGTH_SHORT)
            //       .show();
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(radius)//
                    .direction(direction)// 方向
                    .latitude(latitude)//
                    .longitude(longitude)//
                    .build();
            // 设置定位数据
            bdMap.setMyLocationData(locData);
            LatLng ll = new LatLng(latitude, longitude);
            setMarker(ll);
            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(ll);
            bdMap.animateMapStatus(msu);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //适配6.0以上机型请求权限
        PermissionGen.with(SearchMapActivity.this)
                .addRequestCode(100)
                .permissions(
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .request();

        mapView.onResume();
    }

    //以下三个方法用于6.0以上权限申请适配
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        //Toast.makeText(this, "相关权限已允许", Toast.LENGTH_SHORT).show();
    }

    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        //Toast.makeText(this, "相关权限已拒绝", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        poiSearch.destroy();// 释放poi检索对象
        mapView.onDestroy();

        locationClient.unRegisterLocationListener(locationListener);
        locationClient.stop();
    }

    class getData extends AsyncTask<String, String, String> {

        String text_key = editSearchKeyEt.getText().toString();


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            sugAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.item_textview);
            editSearchKeyEt.setAdapter(sugAdapter);
            sugAdapter.notifyDataSetChanged();

        }

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            String keyword = text_key;
            suggestionSearch.requestSuggestion((new SuggestionSearchOption())
                    .keyword(keyword).city(city));
            return null;
        }
    }
}
