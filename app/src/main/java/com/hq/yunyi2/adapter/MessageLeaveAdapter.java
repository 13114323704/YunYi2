package com.hq.yunyi2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.hq.yunyi2.R;
import com.hq.yunyi2.bean.MessageLeaveBean;

import java.util.List;


public class MessageLeaveAdapter extends BaseAdapter{
    private Context context;
    private List<MessageLeaveBean> list;
    public MessageLeaveAdapter(Context context, List<MessageLeaveBean> list){
        this.context=context;
        this.list=list;
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
            //引入布局
            convertView=View.inflate(context, R.layout.item_message_leave_listview,null);
            //实例化对象
            holder.publish_user_image=(ImageView)convertView.findViewById(R.id.publish_user_image);
            holder.publish_user_nickname=(TextView)convertView.findViewById(R.id.publish_nickname);
            holder.publish_time=(TextView)convertView.findViewById(R.id.publish_time);
            holder.publish_content_text=(TextView)convertView.findViewById(R.id.publish_content_text);

            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        //给控件赋值
        MessageLeaveBean messageLeaveBean=list.get(position);
        holder.publish_user_image.setImageResource(messageLeaveBean.getPublish_user_image());
        holder.publish_user_nickname.setText(messageLeaveBean.getPublish_user_nickname());
        holder.publish_time.setText(messageLeaveBean.getPublish_time());
        if(messageLeaveBean.getPublish_content_text()==null&&messageLeaveBean.getPublish_content_text().equals("")){
            holder.publish_content_text.setText("");
        }else{
            holder.publish_content_text.setText(messageLeaveBean.getPublish_content_text());
        }
        return convertView;
    }

    class ViewHolder{
        ImageView publish_user_image;
        TextView publish_user_nickname;
        TextView publish_time;
        TextView publish_content_text;
    }
}
