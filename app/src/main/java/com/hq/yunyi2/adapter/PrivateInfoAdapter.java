package com.hq.yunyi2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.hq.yunyi2.R;
import com.hq.yunyi2.bean.PrivateInfoBean;

import java.util.List;


public class PrivateInfoAdapter extends BaseAdapter {
    private Context context;
    private List<PrivateInfoBean> list;

    public PrivateInfoAdapter(Context context, List<PrivateInfoBean> list) {
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
        ViewHolder holder=null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.item_private_info_listview,null);
            holder.textView_name=(TextView)convertView.findViewById(R.id.textview_info_name);
            holder.textView_motto=(TextView)convertView.findViewById(R.id.textview_info_motto);
            holder.textView_relation=(TextView)convertView.findViewById(R.id.textview_info_relation);
            holder.textView_experience_content=(TextView)convertView.findViewById(R.id.textview_info_content);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        PrivateInfoBean privateInfoBean=list.get(position);
        holder.textView_name.setText(privateInfoBean.getName());
        holder.textView_motto.setText(privateInfoBean.getMotto());
        holder.textView_relation.setText(privateInfoBean.getRelation());
        holder.textView_experience_content.setText(privateInfoBean.getExperience_content());
        return convertView;
    }

    class ViewHolder{
        TextView textView_name;
        TextView textView_motto;
        TextView textView_relation;
        TextView textView_experience_content;
    }
}
