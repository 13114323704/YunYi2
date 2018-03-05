package com.hq.yunyi2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hq.yunyi2.R;
import com.hq.yunyi2.bean.FamilyCircleBean;

import java.util.List;


public class FamilyCircleAdapter extends BaseAdapter {

    private Context context;
    private List<FamilyCircleBean> list;

    public FamilyCircleAdapter(Context context, List<FamilyCircleBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //引入布局
        convertView = LayoutInflater.from(context).inflate( R.layout.item_family_listview, null);
        //实例化对象
        ImageView publish_user_image= (ImageView) convertView.findViewById(R.id.publish_user_image);
        TextView publish_user_nickname = (TextView) convertView.findViewById(R.id.publish_nickname);
        TextView publish_time = (TextView) convertView.findViewById(R.id.publish_time);
        TextView publish_content_text = (TextView) convertView.findViewById(R.id.publish_content_text);
        ImageView publish_content_image = (ImageView) convertView.findViewById(R.id.publish_content_image);
        //给控件赋值
        FamilyCircleBean familyCircleBean = list.get(position);

        publish_user_image.setImageResource(familyCircleBean.getPublish_user_image());
        publish_user_nickname.setText(familyCircleBean.getPublish_user_nickname());
        publish_time.setText(familyCircleBean.getPublish_time());
        if (familyCircleBean.getPublish_content_text() == null && familyCircleBean.getPublish_content_text().equals("")) {
            publish_content_text.setText("");
        } else {
            publish_content_text.setText(familyCircleBean.getPublish_content_text());
        }
        if (familyCircleBean.getPublish_content_image() == null) {
            publish_content_image.setVisibility(View.GONE);
        } else {
            publish_content_image.setImageDrawable(familyCircleBean.getPublish_content_image());
        }
        return convertView;
    }

}
