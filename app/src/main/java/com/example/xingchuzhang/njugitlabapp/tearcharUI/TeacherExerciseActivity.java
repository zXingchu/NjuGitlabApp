package com.example.xingchuzhang.njugitlabapp.tearcharUI;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xingchuzhang.njugitlabapp.R;
import com.example.xingchuzhang.njugitlabapp.adapter.MyAdapter;
import com.example.xingchuzhang.njugitlabapp.studentUI.StuAnalysisActivity;
import com.example.xingchuzhang.njugitlabapp.utilities.JSONToMapList;
import com.example.xingchuzhang.njugitlabapp.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherExerciseActivity extends AppCompatActivity {

    private ListView listView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_exercise);
        listView=(ListView)findViewById(R.id.exerList);
        // Use the getIntent method to store the Intent that started this Activity in a variable
        Intent intent=getIntent();
        //Create an if statement to check if this Intent has the extra we passed from MainActivity
        if(intent.hasExtra(Intent.EXTRA_TEXT)) {
            String jsonStr=intent.getStringExtra(Intent.EXTRA_TEXT);
            try {
                JSONArray jsonData=new JSONArray(jsonStr);
                List<Map<String, Object>> dataList= JSONToMapList.transformateJSONToMapList(jsonData);
                listView.setAdapter(new MyAdapter(this, dataList));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String,String> map=(HashMap<String,String>)listView.getItemAtPosition(position);
                        String assignId=map.get("id");
                        Toast.makeText(getApplicationContext(),
                                "你选择了第"+position+"个Item，assignId的值是："+assignId,
                                Toast.LENGTH_SHORT).show();
                        if(NetworkUtils.ISSTUDENT){
                            new GitlabConnectTask().execute(NetworkUtils.ASSIGNMENT+"/"+assignId+"/"
                                    +NetworkUtils.STUDENT+"/"+NetworkUtils.STUID+"/"+NetworkUtils.ANALYSIS);
                        }else{
                            new GitlabConnectTask().execute(NetworkUtils.ASSIGNMENT+"/"+assignId+"/"+NetworkUtils.SCORE);
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleOperation(String respStr) {
        Class destinationActivity = null;
        if(NetworkUtils.ISSTUDENT)
            destinationActivity=StuAnalysisActivity.class;
        else
            destinationActivity= StatisticsInfoActivity.class;
        Intent startChildActivityIntent = new Intent(this, destinationActivity);
        //添加字符串信息到Intent
        startChildActivityIntent.putExtra(Intent.EXTRA_TEXT,respStr);
        //启动另一个Activity
        startActivity(startChildActivityIntent);
    }

    public class GitlabConnectTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String respStr = NetworkUtils.getResponseFromHttpUrl(params[0],null);
            System.out.println(respStr);
            return respStr;
        }

        @Override
        protected void onPostExecute(String respStr) {
            if (respStr != null && !respStr.equals("")) {
                handleOperation(respStr);
            }
        }
    }

}
