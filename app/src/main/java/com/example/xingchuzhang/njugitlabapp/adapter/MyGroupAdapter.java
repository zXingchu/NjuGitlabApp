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

public class MyGroupAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public MyGroupAdapter(Context context, List<Map<String, Object>> data){
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
        GroupViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new GroupViewHolder();
            //获得组件，实例化组件
            convertView=layoutInflater.inflate(R.layout.list_group, null);
            viewHolder.idText=(TextView)convertView.findViewById(R.id.gIdText);
            viewHolder.nameText=(TextView)convertView.findViewById(R.id.gNameText);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(GroupViewHolder)convertView.getTag();
        }
        //绑定数据
        viewHolder.idText.setText((String)data.get(position).get("id"));
        viewHolder.nameText.setText((String)data.get(position).get("name"));
        return convertView;
    }

    public final class GroupViewHolder{
        public TextView idText;
        public TextView nameText;
    }
}
