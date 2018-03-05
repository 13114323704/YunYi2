package com.hq.yunyi2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ServiceListActivity extends Activity implements View.OnClickListener {

    private Button btn_new_style, btn_old_style;
    private PullRefreshLayout pullRefreshLayout;
    //用于处理下拉刷新事件
    private Handler pullRefresh_handler = new Handler();
    //图片轮播相关
    private ViewPager mViewPaper;
    private List<ImageView> images;
    // 存放图片的id
    private int[] imageIds = new int[]{R.mipmap.a, R.mipmap.b,
            R.mipmap.c, R.mipmap.d, R.mipmap.e};
    private List<View> dots;
    private int currentItem;
    // 记录上一次点的位置
    private int oldPosition = 0;
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;
    //接收子线程传递过来的数据,用于处理图片轮播事件
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);
        initData();
        initView();
        initListener();
    }

    public void image_back(View view) {
        Intent intent=new Intent(ServiceListActivity.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

    private void initData() {
        // 显示的图片
        images = new ArrayList<ImageView>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(ServiceListActivity.this);
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        // 显示的小点
        dots = new ArrayList<View>();
        dots.add(this.findViewById(R.id.dot_0));
        dots.add(this.findViewById(R.id.dot_1));
        dots.add(this.findViewById(R.id.dot_2));
        dots.add(this.findViewById(R.id.dot_3));
        dots.add(this.findViewById(R.id.dot_4));
    }

    private void initView() {

        pullRefreshLayout = (PullRefreshLayout) this.findViewById(R.id.pullRefreshLayout);
        mViewPaper = (ViewPager) this.findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter);


        btn_new_style = (Button) this.findViewById(R.id.btn_new_style);
        btn_old_style = (Button) this.findViewById(R.id.btn_old_style);
    }

    private void initListener() {
        btn_new_style.setOnClickListener(this);
        btn_old_style.setOnClickListener(this);

        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullRefresh_handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullRefreshLayout.setRefreshing(false);
                        Toast.makeText(ServiceListActivity.this, "刷新完成啦", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });

        mViewPaper.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                dots.get(position).setBackgroundResource(
                        R.drawable.dot_focused);
                dots.get(oldPosition).setBackgroundResource(
                        R.drawable.dot_normal);

                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    //利用线程池定时执行动画轮播
    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPageTask(), 3,
                3, TimeUnit.SECONDS);
    }

    @Override
    public void onStop() {
        super.onStop();
        scheduledExecutorService.shutdown();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_new_style:
                Toast.makeText(ServiceListActivity.this,"新",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_old_style:
                Toast.makeText(ServiceListActivity.this,"旧",Toast.LENGTH_SHORT).show();
                break;
        }


    }

    private class ViewPageTask implements Runnable {

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
            // super.destroyItem(container, position, object);
            // view.removeView(view.getChildAt(position));
            // view.removeViewAt(position);
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(images.get(position));
            return images.get(position);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(ServiceListActivity.this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        }
        return super.onKeyDown(keyCode, event);
    }
}


