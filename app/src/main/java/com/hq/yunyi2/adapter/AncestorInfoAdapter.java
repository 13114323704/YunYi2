package com.hq.yunyi2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hq.yunyi2.R;
import com.hq.yunyi2.bean.AncestorInfoBean;

import java.util.List;


public class AncestorInfoAdapter extends BaseAdapter {
    private Context context;
    private List<AncestorInfoBean> list;

    public AncestorInfoAdapter(Context context, List<AncestorInfoBean> list) {
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
            convertView = View.inflate(context, R.layout.item_ancestor_listview, null);
            //实例化对象
            holder.text_name = (TextView) convertView.findViewById(R.id.textview_name);
            holder.text_relative = (TextView) convertView.findViewById(R.id.textview_relative);
            holder.text_address = (TextView) convertView.findViewById(R.id.textview_address);
            holder.text_time = (TextView) convertView.findViewById(R.id.textview_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //给控件赋值
        AncestorInfoBean ancestorInfoBean = list.get(position);
        holder.text_name.setText(ancestorInfoBean.getName());
        holder.text_relative.setText(ancestorInfoBean.getRelative());
        holder.text_address.setText(ancestorInfoBean.getAddress());
        holder.text_time.setText(ancestorInfoBean.getTime());
        return convertView;
    }

    class ViewHolder {

        TextView text_name;
        TextView text_relative;
        TextView text_address;
        TextView text_time;
    }
}
