package com.hq.yunyi2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;

import com.hq.yunyi2.adapter.PrivateInfoAdapter;
import com.hq.yunyi2.bean.PrivateInfoBean;
import com.hq.yunyi2.utils.GsonTools;
import com.hq.yunyi2.utils.PrivateInfoHttpUtils;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrivateInfoActivity extends Activity {

    private ListView listView;
    private List<PrivateInfoBean> list = new ArrayList<>();
    private PrivateInfoAdapter privateInfoAdapter;
    private TextView textView_create;
    private PullRefreshLayout pullRefreshLayout;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String string_privateinfos = (String) msg.obj;
            if (msg.what == 1) {
                try {
                    list.clear();
                    List<PrivateInfoBean> list_private = GsonTools.getDataList(string_privateinfos, PrivateInfoBean[].class);
                    for (int i = 0; i < list_private.size(); i++) {
                        list.add(0, new PrivateInfoBean(URLDecoder.decode(list_private.get(i).getName(), "utf-8"), URLDecoder.decode(list_private.get(i).getMotto(), "utf-8"), URLDecoder.decode(list_private.get(i).getBirth_date(), "utf-8"), URLDecoder.decode(list_private.get(i).getDied_date(), "utf-8"), URLDecoder.decode(list_private.get(i).getRelation(), "utf-8"), URLDecoder.decode(list_private.get(i).getEducation(), "utf-8"), URLDecoder.decode(list_private.get(i).getJob(), "utf-8"), URLDecoder.decode(list_private.get(i).getExperience_content(), "utf-8")));
                    }
                    privateInfoAdapter.notifyDataSetChanged();
                    pullRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    Toast.makeText(PrivateInfoActivity.this, "服务器未打开或出错啦", Toast.LENGTH_LONG).show();
                    pullRefreshLayout.setRefreshing(false);
                }

            } else {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PrivateInfoActivity.this, "刷新失败", Toast.LENGTH_SHORT).show();
                        pullRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_info);
        listView = (ListView) this.findViewById(R.id.listview_private_info);
        textView_create = (TextView) this.findViewById(R.id.textView_create);
        pullRefreshLayout = (PullRefreshLayout) this.findViewById(R.id.pullRefreshLayout_private_info);
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshListView();
            }
        });

        privateInfoAdapter = new PrivateInfoAdapter(PrivateInfoActivity.this, list);
        listView.setAdapter(privateInfoAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PrivateInfoActivity.this, PrivateInfoDetailActivity.class);
                intent.putExtra("name", list.get(position).getName());
                intent.putExtra("motto", list.get(position).getMotto());
                intent.putExtra("birth_date", list.get(position).getBirth_date());
                intent.putExtra("died_date", list.get(position).getDied_date());
                intent.putExtra("relation", list.get(position).getRelation());
                intent.putExtra("education", list.get(position).getEducation());
                intent.putExtra("job", list.get(position).getJob());
                intent.putExtra("content", list.get(position).getExperience_content());
                PrivateInfoActivity.this.startActivity(intent);
            }
        });
        textView_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(PrivateInfoActivity.this, CreatePrivateInfoActivity.class), 1);
            }
        });

        pullRefreshLayout.setRefreshing(true);
        refreshListView();

    }

    public void image_back(View view) {
        Intent intent=new Intent(PrivateInfoActivity.this,PrivateCenterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {

        }
    }

    private void refreshListView() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("flag", "1");
                        String result_json = PrivateInfoHttpUtils.sendPostMessage(params, "utf-8");
                        Message message = Message.obtain();
                        message.obj = result_json;
                        message.what = 1;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        Message message = Message.obtain();
                        message.what = 2;
                        handler.sendMessage(message);
                    }

                }
            }).start();
        } catch (Exception e) {
            Toast.makeText(PrivateInfoActivity.this, "服务器未打开或出错啦", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(PrivateInfoActivity.this,PrivateCenterActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        }
        return super.onKeyDown(keyCode, event);
    }
}

