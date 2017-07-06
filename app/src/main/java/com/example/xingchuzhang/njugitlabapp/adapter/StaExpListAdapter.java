package com.example.xingchuzhang.njugitlabapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.xingchuzhang.njugitlabapp.R;
import com.example.xingchuzhang.njugitlabapp.other.ScoreHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Xingchu Zhang on 2017/7/4 0004.
 */

public class StaExpListAdapter extends BaseExpandableListAdapter {

    private JSONArray data;
    private LayoutInflater layoutInflater;
    private Context context;

    public StaExpListAdapter(Context context, JSONArray data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return data.length();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        try {
            return data.getJSONObject(groupPosition).getJSONArray("questionResults").length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        try {
            return data.getJSONObject(groupPosition);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        try {
            return data.getJSONObject(groupPosition).getJSONArray("questionResults").getJSONObject(childPosition);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.expendlist_assign_score, null);
            groupHolder = new GroupViewHolder();
            groupHolder.idText = (TextView)convertView.findViewById(R.id.idText);
            groupHolder.scoreText = (TextView)convertView.findViewById(R.id.scoreText);
            convertView.setTag(groupHolder);
        }
        else {
            groupHolder = (GroupViewHolder)convertView.getTag();
        }
        try {
            groupHolder.idText.setText(data.getJSONObject(groupPosition).getString("assignmentId"));
            if(data.getJSONObject(groupPosition).getJSONArray("questionResults").length()!=0)
                groupHolder.scoreText.setText(data.getJSONObject(groupPosition).getString("aScore"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder itemHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.expendlist_question_score, null);
            itemHolder = new ChildViewHolder();
            itemHolder.qIdText = (TextView)convertView.findViewById(R.id.qIdText);
            itemHolder.qScoreText = (TextView)convertView.findViewById(R.id.qScoreText);
            convertView.setTag(itemHolder);
        }
        else {
            itemHolder = (ChildViewHolder)convertView.getTag();
        }
        try {
            JSONObject jsonObject=(JSONObject) getChild(groupPosition,childPosition);
            itemHolder.qIdText.setText(jsonObject.getString("questionId"));
            itemHolder.qScoreText.setText(jsonObject.getJSONObject("scoreResult").getString("score"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private final class GroupViewHolder{
        public TextView idText;
        public TextView scoreText;
    }

    private final class ChildViewHolder{
        public TextView qIdText;
        public TextView qScoreText;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
