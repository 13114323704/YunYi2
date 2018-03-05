package com.hq.yunyi2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hq.yunyi2.R;
import com.hq.yunyi2.bean.PrivateExperienceBean;

import java.util.List;


public class PrivateExperienceAdapter extends BaseAdapter {

    private Context context;
    private List<PrivateExperienceBean> list;


    public PrivateExperienceAdapter(Context context, List<PrivateExperienceBean> list) {
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
            convertView = View.inflate(context, R.layout.item_private_experience_listview, null);
            holder.textView_title = (TextView) convertView.findViewById(R.id.private_experience_title);
            holder.textView_time = (TextView) convertView.findViewById(R.id.private_experience_time);
            holder.textView_content = (TextView) convertView.findViewById(R.id.private_experience_content);
            holder.imageView = (ImageView) convertView.findViewById(R.id.private_experience_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PrivateExperienceBean privateExperienceBean = list.get(position);
        holder.textView_title.setText(privateExperienceBean.getTitle());
        holder.textView_time.setText(privateExperienceBean.getTime());
        holder.textView_content.setText(privateExperienceBean.getContent());
        holder.imageView.setImageDrawable(privateExperienceBean.getImage());
        return convertView;
    }

    class ViewHolder {
        TextView textView_title;
        TextView textView_time;
        TextView textView_content;
        ImageView imageView;
    }
}
