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

public class MyAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public MyAdapter(Context context, List<Map<String, Object>> data){
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
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            //获得组件，实例化组件
            convertView=layoutInflater.inflate(R.layout.list_exam, null);
            viewHolder.idText=(TextView)convertView.findViewById(R.id.idText);
            viewHolder.titleText=(TextView)convertView.findViewById(R.id.titleText);
            viewHolder.infoText=(TextView)convertView.findViewById(R.id.infoText);
            viewHolder.stimeText=(TextView)convertView.findViewById(R.id.stimeText);
            viewHolder.etimeText=(TextView)convertView.findViewById(R.id.etimeText);
            viewHolder.statusText=(TextView)convertView.findViewById(R.id.statusText);
            viewHolder.courseText=(TextView)convertView.findViewById(R.id.courseText);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        //绑定数据
        viewHolder.idText.setText((String)data.get(position).get("id"));
        viewHolder.titleText.setText((String)data.get(position).get("title"));
        viewHolder.courseText.setText((String)data.get(position).get("course"));
        viewHolder.statusText.setText((String)data.get(position).get("status"));
        viewHolder.infoText.setText((String)data.get(position).get("description"));
        viewHolder.stimeText.setText((String)data.get(position).get("startAt"));
        viewHolder.etimeText.setText((String)data.get(position).get("endAt"));
        return convertView;
    }

    public final class ViewHolder{
        public TextView idText;
        public TextView titleText;
        public TextView infoText;
        public TextView stimeText;
        public TextView etimeText;
        public TextView statusText;
        public TextView courseText;

    }

}
