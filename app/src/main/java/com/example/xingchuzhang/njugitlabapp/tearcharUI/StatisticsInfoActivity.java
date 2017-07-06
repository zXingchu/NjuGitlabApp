package com.example.xingchuzhang.njugitlabapp.tearcharUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xingchuzhang.njugitlabapp.R;
import com.example.xingchuzhang.njugitlabapp.adapter.MyAdapter;
import com.example.xingchuzhang.njugitlabapp.adapter.MyStatisticAdapter;
import com.example.xingchuzhang.njugitlabapp.adapter.MyStudentsAdapter;
import com.example.xingchuzhang.njugitlabapp.utilities.JSONToMapList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsInfoActivity extends AppCompatActivity {

    private ListView listView=null;

    private TextView assignIdText=null;

    private TextView assignNumberText=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_info);
        assignIdText=(TextView)findViewById(R.id.assignText);
        assignNumberText=(TextView)findViewById(R.id.assignNumberText);
        listView=(ListView)findViewById(R.id.statisticList);
        Intent intent=getIntent();
        //Create an if statement to check if this Intent has the extra we passed from MainActivity
        if(intent.hasExtra(Intent.EXTRA_TEXT)) {
            String jsonStr=intent.getStringExtra(Intent.EXTRA_TEXT);
            try {
                JSONObject jsonData=new JSONObject(jsonStr);
                assignIdText.setText(jsonData.getString("assignmentId"));
                assignNumberText.setText(String.valueOf(
                        jsonData.getJSONArray("questions")
                                .getJSONObject(0)
                                .getJSONArray("students")
                                .length()));
                List<Map<String, Object>> dataList= JSONToMapList.tScoreJSONToMapList(jsonData);
                listView.setAdapter(new MyStatisticAdapter(this, dataList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
