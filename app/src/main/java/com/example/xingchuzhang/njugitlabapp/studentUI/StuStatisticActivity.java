package com.example.xingchuzhang.njugitlabapp.studentUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.xingchuzhang.njugitlabapp.R;
import com.example.xingchuzhang.njugitlabapp.adapter.MyExpandableListAdapter;
//import com.example.xingchuzhang.njugitlabapp.adapter.StaExpListAdapter;
import com.example.xingchuzhang.njugitlabapp.adapter.StaExpListAdapter;
import com.example.xingchuzhang.njugitlabapp.other.ScoreHelper;
import com.example.xingchuzhang.njugitlabapp.utilities.JSONToMapList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StuStatisticActivity extends AppCompatActivity {

    private ExpandableListView expandableListView=null;

    private TextView scoreText=null;

    private TextView averageScoreText=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_statistic);
        averageScoreText=(TextView)findViewById(R.id.averageText);
        scoreText=(TextView)findViewById(R.id.scoreText);
        Intent intent=getIntent();
        if(intent.hasExtra(Intent.EXTRA_TEXT)) {
            String jsonStr=intent.getStringExtra(Intent.EXTRA_TEXT);
            System.out.println(jsonStr);
            try {
                JSONArray jsonData=new JSONArray(jsonStr);
                averageScoreText.setText(getAverageScore(jsonData));
                scoreText.setText(getAverageScore(jsonData));
                expandableListView = (ExpandableListView)findViewById(R.id.statisticExpendlist);
                expandableListView.setGroupIndicator(null);
                StaExpListAdapter adapter = new StaExpListAdapter(this,jsonData);
                expandableListView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private String getAverageScore(JSONArray jsonData){
        int qScores=0;
        int aScores=0;
        int numAssignment=0;
        int aScore;
        try {
            for (int i=0;i<jsonData.length();i++){
                JSONObject jsonObject=jsonData.getJSONObject(i);
                JSONArray questionArray=jsonObject.getJSONArray("questionResults");
                if(questionArray.length()>=1) {
                    for (int j=0;j<questionArray.length();j++){
                        qScores+=questionArray.getJSONObject(j).getJSONObject("scoreResult").getInt("score");
                    }
                    aScore  = qScores / questionArray.length();
                    aScores+=aScore;
                    jsonData.getJSONObject(i).put("aScore",aScore);
                    numAssignment++;
                    qScores=0;
                }

            }
            if(numAssignment!=0){
                return String.valueOf(aScores/numAssignment);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
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
