package com.example.xingchuzhang.njugitlabapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xingchuzhang.njugitlabapp.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Xingchu Zhang on 2017/6/29 0029.
 */

public class MyStudentsAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public MyStudentsAdapter(Context context, List<Map<String, Object>> data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StudentViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new StudentViewHolder();
            //获得组件，实例化组件
            convertView=layoutInflater.inflate(R.layout.list_student, null);
            viewHolder.sIdText=(TextView)convertView.findViewById(R.id.sIdText);
            viewHolder.sNameText=(TextView)convertView.findViewById(R.id.sNameText);
            viewHolder.groupIdText=(TextView)convertView.findViewById(R.id.groupIdText);
            viewHolder.gitIdText=(TextView)convertView.findViewById(R.id.gitIdText);
            viewHolder.gitNameText=(TextView)convertView.findViewById(R.id.gitNameText);
            viewHolder.sexText=(TextView)convertView.findViewById(R.id.sexText);
            viewHolder.emailText=(TextView)convertView.findViewById(R.id.emailText);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(StudentViewHolder)convertView.getTag();
        }
        //绑定数据
        viewHolder.sIdText.setText((String)data.get(position).get("id"));
        viewHolder.sNameText.setText((String)data.get(position).get("name"));
        viewHolder.groupIdText.setText((String)data.get(position).get("groupId"));
        viewHolder.gitIdText.setText((String)data.get(position).get("gitId"));
        viewHolder.gitNameText.setText((String)data.get(position).get("gitUsername"));
        viewHolder.sexText.setText((String)data.get(position).get("gender"));
        viewHolder.emailText.setText((String)data.get(position).get("email"));
        return convertView;
    }

    public final class StudentViewHolder{
        public TextView sIdText;
        public TextView sNameText;
        public TextView groupIdText;
        public TextView gitIdText;
        public TextView gitNameText;
        public TextView sexText;
        public TextView emailText;
    }

}
