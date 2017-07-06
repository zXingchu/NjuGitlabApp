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
 * Created by Xingchu Zhang on 2017/6/30 0030.
 */

public class MyStatisticAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public MyStatisticAdapter(Context context, List<Map<String, Object>> data){
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
        StatisticViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new StatisticViewHolder();
            //获得组件，实例化组件
            convertView=layoutInflater.inflate(R.layout.list_statistic, null);
            viewHolder.qIdText=(TextView)convertView.findViewById(R.id.qIdText);
            viewHolder.qTitleText=(TextView)convertView.findViewById(R.id.qTitleText);
            viewHolder.qInfoText=(TextView)convertView.findViewById(R.id.qInfoText);
            viewHolder.qTypeText=(TextView)convertView.findViewById(R.id.qTypeText);
            viewHolder.Section0_10=(TextView)convertView.findViewById(R.id.Section0_10);
            viewHolder.Section10_20=(TextView)convertView.findViewById(R.id.Section10_20);
            viewHolder.Section20_30=(TextView)convertView.findViewById(R.id.Section20_30);
            viewHolder.Section30_40=(TextView)convertView.findViewById(R.id.Section30_40);
            viewHolder.Section40_50=(TextView)convertView.findViewById(R.id.Section40_50);
            viewHolder.Section50_60=(TextView)convertView.findViewById(R.id.Section50_60);
            viewHolder.Section60_70=(TextView)convertView.findViewById(R.id.Section60_70);
            viewHolder.Section70_80=(TextView)convertView.findViewById(R.id.Section70_80);
            viewHolder.Section80_90=(TextView)convertView.findViewById(R.id.Section80_90);
            viewHolder.Section90_100=(TextView)convertView.findViewById(R.id.Section90_100);
            viewHolder.Section100=(TextView)convertView.findViewById(R.id.Section100);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(StatisticViewHolder)convertView.getTag();
        }
        //绑定数据
        viewHolder.qIdText.setText((String)data.get(position).get("id"));
        viewHolder.qTitleText.setText((String)data.get(position).get("title"));
        viewHolder.qInfoText.setText((String)data.get(position).get("description"));
        viewHolder.qTypeText.setText((String)data.get(position).get("type"));
        int[] sectionNumber= (int[]) data.get(position).get("sectionNumber");
        viewHolder.Section0_10.setText(String.valueOf(sectionNumber[0]));
        viewHolder.Section10_20.setText(String.valueOf(sectionNumber[1]));
        viewHolder.Section20_30.setText(String.valueOf(sectionNumber[2]));
        viewHolder.Section30_40.setText(String.valueOf(sectionNumber[3]));
        viewHolder.Section40_50.setText(String.valueOf(sectionNumber[4]));
        viewHolder.Section50_60.setText(String.valueOf(sectionNumber[5]));
        viewHolder.Section60_70.setText(String.valueOf(sectionNumber[6]));
        viewHolder.Section70_80.setText(String.valueOf(sectionNumber[7]));
        viewHolder.Section80_90.setText(String.valueOf(sectionNumber[8]));
        viewHolder.Section90_100.setText(String.valueOf(sectionNumber[9]));
        viewHolder.Section100.setText(String.valueOf(sectionNumber[10]));
        return convertView;
    }

    public final class StatisticViewHolder{
        public TextView qIdText;
        public TextView qTitleText;
        public TextView qInfoText;
        public TextView qTypeText;
        public TextView Section0_10;
        public TextView Section10_20;
        public TextView Section20_30;
        public TextView Section30_40;
        public TextView Section40_50;
        public TextView Section50_60;
        public TextView Section60_70;
        public TextView Section70_80;
        public TextView Section80_90;
        public TextView Section90_100;
        public TextView Section100;
    }

}
