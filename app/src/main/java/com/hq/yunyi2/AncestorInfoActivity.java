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
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.hq.yunyi2.adapter.AncestorInfoAdapter;
import com.hq.yunyi2.bean.AncestorInfoBean;
import com.hq.yunyi2.utils.AncestorInfoHttpUtils;
import com.hq.yunyi2.utils.GsonTools;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AncestorInfoActivity extends Activity {

    private ListView listView_ancestor;
    private PullRefreshLayout pullRefreshLayout;
    private List<AncestorInfoBean> list = new ArrayList<>();
    private AncestorInfoAdapter ancestorInfoAdapter;
    private String name,relative,address, time;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String string_result = (String) msg.obj;
            if (msg.what == 1) {
                try {
                    list.clear();
                    List<AncestorInfoBean> list_private = GsonTools.getDataList(string_result, AncestorInfoBean[].class);
                    for (int i = 0; i < list_private.size(); i++) {
                        list.add(0, new AncestorInfoBean(URLDecoder.decode(list_private.get(i).getName(), "utf-8"), URLDecoder.decode(list_private.get(i).getRelative(), "utf-8"), URLDecoder.decode(list_private.get(i).getAddress(), "utf-8"),URLDecoder.decode(list_private.get(i).getTime(), "utf-8")));
                    }
                    ancestorInfoAdapter.notifyDataSetChanged();
                    pullRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    Toast.makeText(AncestorInfoActivity.this, "服务器未打开或出错啦", Toast.LENGTH_LONG).show();
                    pullRefreshLayout.setRefreshing(false);
                }

            } else if (msg.what == 2) {
                if (string_result.equals("success")) {
                    Toast.makeText(AncestorInfoActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AncestorInfoActivity.this, "添加失败", Toast.LENGTH_LONG).show();
                }
                refreshListView();
            } else if (msg.what == 3) {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AncestorInfoActivity.this, "刷新失败", Toast.LENGTH_SHORT).show();
                        pullRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ancestor_info);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        relative = intent.getStringExtra("relative");
        address=intent.getStringExtra("address");
        time = intent.getStringExtra("time");

        ancestorInfoAdapter = new AncestorInfoAdapter(AncestorInfoActivity.this, list);
        listView_ancestor = (ListView) this.findViewById(R.id.listview_ancestor);
        listView_ancestor.setAdapter(ancestorInfoAdapter);

        pullRefreshLayout = (PullRefreshLayout) this.findViewById(R.id.pullRefreshLayout_ancestor_info);
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshListView();
            }
        });
        pullRefreshLayout.setRefreshing(true);
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(relative)||TextUtils.isEmpty(address)||TextUtils.isEmpty(time)) {
            refreshListView();
        } else {
            addData();
        }

        listView_ancestor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Snackbar snackbar = Snackbar.make(view, list.get(position).getAddress(), Snackbar.LENGTH_INDEFINITE);
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

    public void image_back(View view) {
        Intent intent=new Intent(AncestorInfoActivity.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

    public void add(View view) {
        Intent intent = new Intent(AncestorInfoActivity.this, CreateAncestorInfoActivity.class);
        startActivity(intent);
    }

    private void addData() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("flag", "2");
                        params.put("name", name);
                        params.put("relative", relative);
                        params.put("address",address);
                        params.put("time", time);
                        String result_json = AncestorInfoHttpUtils.sendPostMessage(params, "utf-8");
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
            Toast.makeText(AncestorInfoActivity.this, "服务器未打开或出错啦", Toast.LENGTH_LONG).show();
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
                        String result_json = AncestorInfoHttpUtils.sendPostMessage(params, "utf-8");
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
            Toast.makeText(AncestorInfoActivity.this, "服务器未打开或出错啦", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(AncestorInfoActivity.this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        }
        return super.onKeyDown(keyCode, event);
    }
}
