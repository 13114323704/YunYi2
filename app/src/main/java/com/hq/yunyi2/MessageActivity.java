package com.hq.yunyi2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.hq.yunyi2.utils.TabLayoutUtils;

import java.util.ArrayList;


public class MessageActivity extends Activity {

    private View view_message_public_person_date, view_message_family_similar;
    private View view_public_person_date1,view_public_person_date2,view_public_person_date3;
    private View view_public_person_date4,view_public_person_date5,view_public_person_date6;
    private View view_family_similar1,view_family_similar2,view_family_similar3;
    private View view_family_similar4,view_family_similar5,view_family_similar6;
    private PullRefreshLayout pullRefreshLayout_message_public_person_date, pullRefreshLayout_message_family_similar;
    private Handler handler = new Handler();

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ArrayList<View> viewContainter = new ArrayList<View>();
    private ArrayList<String> titleContainer = new ArrayList<String>();
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        view_message_public_person_date = LayoutInflater.from(MessageActivity.this).inflate(R.layout.message_public_person_date, null);
        view_message_family_similar = LayoutInflater.from(MessageActivity.this).inflate(R.layout.message_family_similar, null);

        view_public_person_date1=view_message_public_person_date.findViewById(R.id.message_public_date1);
        view_public_person_date2=view_message_public_person_date.findViewById(R.id.message_public_date2);
        view_public_person_date3=view_message_public_person_date.findViewById(R.id.message_public_date3);
        view_public_person_date4=view_message_public_person_date.findViewById(R.id.message_public_date4);
        view_public_person_date5=view_message_public_person_date.findViewById(R.id.message_public_date5);
        view_public_person_date6=view_message_public_person_date.findViewById(R.id.message_public_date6);
        view_public_person_date3.setVisibility(View.GONE);
        view_public_person_date4.setVisibility(View.GONE);
        view_public_person_date5.setVisibility(View.GONE);
        view_public_person_date6.setVisibility(View.GONE);

        view_family_similar1=view_message_family_similar.findViewById(R.id.message_family_similar_layout1);
        view_family_similar2=view_message_family_similar.findViewById(R.id.message_family_similar_layout2);
        view_family_similar3=view_message_family_similar.findViewById(R.id.message_family_similar_layout3);
        view_family_similar4=view_message_family_similar.findViewById(R.id.message_family_similar_layout4);
        view_family_similar5=view_message_family_similar.findViewById(R.id.message_family_similar_layout5);
        view_family_similar6=view_message_family_similar.findViewById(R.id.message_family_similar_layout6);
        view_family_similar4.setVisibility(View.GONE);
        view_family_similar5.setVisibility(View.GONE);
        view_family_similar6.setVisibility(View.GONE);

        viewContainter.clear();
        titleContainer.clear();

        viewContainter.add(view_message_public_person_date);
        viewContainter.add(view_message_family_similar);

        titleContainer.add("公众人物诞辰");
        titleContainer.add("家族信息");
    }

    private void initView() {
        viewPager = (ViewPager) this.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) this.findViewById(R.id.tablayout);
        adapter = new PagerAdapter() {
            // viewpager中的组件数量
            @Override
            public int getCount() {
                return viewContainter.size();
            }

            // 滑动切换的时候销毁当前的组件
            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(viewContainter.get(position));
            }

            // 每次滑动的时候生成的组件
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewContainter.get(position));
                return viewContainter.get(position);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getItemPosition(Object object) {

                return super.getItemPosition(object);
            }

            @Override
            public CharSequence getPageTitle(int position) {

                return titleContainer.get(position);
            }

        };

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //TabLayoutUtils.setIndicator(tabLayout, 40, 40);

        pullRefreshLayout_message_public_person_date = (PullRefreshLayout) view_message_public_person_date.findViewById(R.id.pullRefreshLayout_message_public_person_date);
        pullRefreshLayout_message_family_similar = (PullRefreshLayout) view_message_family_similar.findViewById(R.id.pullRefreshLayout_message_family_similar);
    }

    private void initListener() {

        pullRefreshLayout_message_public_person_date.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullRefreshLayout_message_public_person_date.setRefreshing(false);
                        if(view_public_person_date1.getVisibility()==View.VISIBLE){
                            view_public_person_date1.setVisibility(View.GONE);
                        }else{
                            view_public_person_date1.setVisibility(View.VISIBLE);
                        }
                        if(view_public_person_date2.getVisibility()==View.VISIBLE){
                            view_public_person_date2.setVisibility(View.GONE);
                        }else{
                            view_public_person_date2.setVisibility(View.VISIBLE);
                        }
                        if(view_public_person_date3.getVisibility()==View.VISIBLE){
                            view_public_person_date3.setVisibility(View.GONE);
                        }else{
                            view_public_person_date3.setVisibility(View.VISIBLE);
                        }
                        if(view_public_person_date4.getVisibility()==View.VISIBLE){
                            view_public_person_date4.setVisibility(View.GONE);
                        }else{
                            view_public_person_date4.setVisibility(View.VISIBLE);
                        }
                        if(view_public_person_date5.getVisibility()==View.VISIBLE){
                            view_public_person_date5.setVisibility(View.GONE);
                        }else{
                            view_public_person_date5.setVisibility(View.VISIBLE);
                        }
                        if(view_public_person_date6.getVisibility()==View.VISIBLE){
                            view_public_person_date6.setVisibility(View.GONE);
                        }else{
                            view_public_person_date6.setVisibility(View.VISIBLE);
                        }
                        Toast.makeText(MessageActivity.this, "消息已更新", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
        pullRefreshLayout_message_family_similar.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullRefreshLayout_message_family_similar.setRefreshing(false);
                        if(view_family_similar1.getVisibility()==View.VISIBLE){
                            view_family_similar1.setVisibility(View.GONE);
                        }else{
                            view_family_similar1.setVisibility(View.VISIBLE);
                        }
                        if(view_family_similar2.getVisibility()==View.VISIBLE){
                            view_family_similar2.setVisibility(View.GONE);
                        }else{
                            view_family_similar2.setVisibility(View.VISIBLE);
                        }
                        if(view_family_similar3.getVisibility()==View.VISIBLE){
                            view_family_similar3.setVisibility(View.GONE);
                        }else{
                            view_family_similar3.setVisibility(View.VISIBLE);
                        }
                        if(view_family_similar4.getVisibility()==View.VISIBLE){
                            view_family_similar4.setVisibility(View.GONE);
                        }else{
                            view_family_similar4.setVisibility(View.VISIBLE);
                        }
                        if(view_family_similar5.getVisibility()==View.VISIBLE){
                            view_family_similar5.setVisibility(View.GONE);
                        }else{
                            view_family_similar5.setVisibility(View.VISIBLE);
                        }
                        if(view_family_similar6.getVisibility()==View.VISIBLE){
                            view_family_similar6.setVisibility(View.GONE);
                        }else{
                            view_family_similar6.setVisibility(View.VISIBLE);
                        }
                        Toast.makeText(MessageActivity.this, "消息已更新", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
    }

    public void image_back(View view) {
        Intent intent=new Intent(MessageActivity.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(MessageActivity.this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        }
        return super.onKeyDown(keyCode, event);
    }

}
