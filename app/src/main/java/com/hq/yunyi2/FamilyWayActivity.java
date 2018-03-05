package com.hq.yunyi2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.hq.yunyi2.adapter.FamilyWayAdapter;
import com.hq.yunyi2.bean.FamilyWayBean;
import com.hq.yunyi2.utils.FamilyWayHttpUtils;
import com.hq.yunyi2.utils.GsonTools;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FamilyWayActivity extends Activity {

    private ListView listView_family_way;
    private PullRefreshLayout pullRefreshLayout;
    private List<FamilyWayBean> list=new ArrayList<>();
    private FamilyWayAdapter familyWayAdapter;
    private TextView textView_create;
    private String string_content,string_time;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String string_result = (String) msg.obj;
            if (msg.what == 1) {
                try {
                    list.clear();
                    List<FamilyWayBean> list_private = GsonTools.getDataList(string_result, FamilyWayBean[].class);
                    for (int i = 0; i < list_private.size(); i++) {
                        list.add(0, new FamilyWayBean(URLDecoder.decode(list_private.get(i).getContent(), "utf-8"), URLDecoder.decode(list_private.get(i).getTime(), "utf-8")));
                    }
                    familyWayAdapter.notifyDataSetChanged();
                    pullRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    Toast.makeText(FamilyWayActivity.this, "服务器未打开或出错啦", Toast.LENGTH_LONG).show();
                    pullRefreshLayout.setRefreshing(false);
                }

            } else if (msg.what == 2) {
                if (string_result.equals("success")) {
                    Toast.makeText(FamilyWayActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(FamilyWayActivity.this, "添加失败"+string_result, Toast.LENGTH_LONG).show();
                }
                refreshListView();
            } else if (msg.what == 3) {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(FamilyWayActivity.this, "刷新失败", Toast.LENGTH_SHORT).show();
                        pullRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_way);

        Intent intent=getIntent();
        string_content=intent.getStringExtra("content");
        string_time=intent.getStringExtra("time");

        textView_create=(TextView)this.findViewById(R.id.textView_create);
        textView_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_to=new Intent(FamilyWayActivity.this,CreateFamilyWayActivity.class);
                startActivity(intent_to);
            }
        });

        pullRefreshLayout=(PullRefreshLayout)this.findViewById(R.id.pullRefreshLayout_family_way);
        familyWayAdapter=new FamilyWayAdapter(FamilyWayActivity.this,list);
        listView_family_way=(ListView)this.findViewById(R.id.listview_family_way);
        listView_family_way.setAdapter(familyWayAdapter);
        pullRefreshLayout = (PullRefreshLayout) this.findViewById(R.id.pullRefreshLayout_family_way);
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshListView();
            }
        });
        pullRefreshLayout.setRefreshing(true);
        if (TextUtils.isEmpty(string_content) || TextUtils.isEmpty(string_time)) {
            refreshListView();
        } else {
            addData();
        }
        listView_family_way.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Snackbar snackbar = Snackbar.make(view, list.get(position).getContent() , Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("关闭", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
                snackbar.setActionTextColor(Color.WHITE);
                TextView textView=(TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextSize(17);
                textView.setMaxLines(20);
                snackbar.show();
                ViewGroup.LayoutParams lp=snackbar.getView().getLayoutParams();
                lp.height= ViewGroup.LayoutParams.WRAP_CONTENT;
                snackbar.getView().setLayoutParams(lp);
            }
        });
    }

    public void image_back(View view){
        Intent intent=new Intent(FamilyWayActivity.this,PrivateCenterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

    private void addData(){
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("flag", "2");
                        params.put("content", string_content);
                        params.put("time", string_time);
                        String result_json = FamilyWayHttpUtils.sendPostMessage(params, "utf-8");
                        Message message = Message.obtain();
                        message.obj = result_json;
                        message.what = 2;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        Message message = Message.obtain();
                        message.what = 3;
                        handler.sendMessage(message);
                    }

                }
            }).start();
        } catch (Exception e) {
            Toast.makeText(FamilyWayActivity.this, "服务器未打开或出错啦", Toast.LENGTH_LONG).show();
        }
    }

    private void refreshListView(){
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("flag", "1");
                        String result_json = FamilyWayHttpUtils.sendPostMessage(params, "utf-8");
                        Message message = Message.obtain();
                        message.obj = result_json;
                        message.what = 1;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        Message message = Message.obtain();
                        message.what = 3;
                        handler.sendMessage(message);
                    }

                }
            }).start();
        } catch (Exception e) {
            Toast.makeText(FamilyWayActivity.this, "服务器未打开或出错啦", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(FamilyWayActivity.this,PrivateCenterActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        }
        return super.onKeyDown(keyCode, event);
    }

}
