package com.example.xingchuzhang.njugitlabapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.xingchuzhang.njugitlabapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Xingchu Zhang on 2017/6/30 0030.
 */

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private List<Map<String, Object>> groupData;
    private ArrayList<List<Map<String, Object>>> childData;
    private LayoutInflater layoutInflater;
    private Context context;

    public MyExpandableListAdapter(Context context, List<Map<String, Object>> groupData, ArrayList<List<Map<String, Object>>> childData){
        this.context=context;
        this.groupData=groupData;
        this.childData=childData;
        this.layoutInflater=LayoutInflater.from(context);
    }


    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.expendlist_question, null);
            groupHolder = new GroupViewHolder();
            groupHolder.qIdText = (TextView)convertView.findViewById(R.id.qIdText);
            groupHolder.qTitleText = (TextView)convertView.findViewById(R.id.qTitleText);
            groupHolder.tCompileText = (TextView)convertView.findViewById(R.id.tCompileText);
            groupHolder.tTestedText = (TextView)convertView.findViewById(R.id.tTestedText);
            groupHolder.qScoreText = (TextView)convertView.findViewById(R.id.qScoreText);
            convertView.setTag(groupHolder);
        }
        else {
            groupHolder = (GroupViewHolder)convertView.getTag();
        }
        groupHolder.qIdText.setText((String)groupData.get(groupPosition).get("questionId"));
        groupHolder.qTitleText.setText((String)groupData.get(groupPosition).get("questionTitle"));
        groupHolder.tCompileText.setText((String)groupData.get(groupPosition).get("compiled"));
        groupHolder.tTestedText.setText((String)groupData.get(groupPosition).get("tested"));
        groupHolder.qScoreText.setText((String)groupData.get(groupPosition).get("score"));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder itemHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.expendlist_testcase, null);
            itemHolder = new ChildViewHolder();
            itemHolder.testNameText = (TextView)convertView.findViewById(R.id.testNameText);
            itemHolder.testPassedText = (TextView)convertView.findViewById(R.id.testPassedText);
            convertView.setTag(itemHolder);
        }
        else {
            itemHolder = (ChildViewHolder)convertView.getTag();
        }
        itemHolder.testNameText.setText((String)childData.get(groupPosition).get(childPosition).get("name"));
        itemHolder.testPassedText.setText((String)childData.get(groupPosition).get(childPosition).get("passed"));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return false;
    }

    private final class GroupViewHolder{
        public TextView qIdText;
        public TextView qTitleText;
        public TextView tCompileText;
        public TextView tTestedText;
        public TextView qScoreText;
    }

    private final class ChildViewHolder{
        public TextView testNameText;
        public TextView testPassedText;
    }

}
