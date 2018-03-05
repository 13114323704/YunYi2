package com.hq.yunyi2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hq.yunyi2.R;
import com.hq.yunyi2.bean.FamilyWayBean;

import java.util.List;


public class FamilyWayAdapter extends BaseAdapter {
    private Context context;
    private List<FamilyWayBean> list;

    public FamilyWayAdapter(Context context, List<FamilyWayBean> list) {
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
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            //引入布局
            convertView = View.inflate(context, R.layout.item_family_way_listview, null);
            //实例化对象
            holder.text_content = (TextView) convertView.findViewById(R.id.textview_content);
            holder.text_time = (TextView) convertView.findViewById(R.id.textview_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FamilyWayBean familyWayBean = list.get(position);
        holder.text_content.setText(familyWayBean.getContent());
        holder.text_time.setText(familyWayBean.getTime());
        return convertView;
    }

    class ViewHolder {

        TextView text_content;
        TextView text_time;
    }
}
