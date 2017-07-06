package com.example.xingchuzhang.njugitlabapp.tearcharUI;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.xingchuzhang.njugitlabapp.R;
import com.example.xingchuzhang.njugitlabapp.adapter.MyGroupAdapter;
import com.example.xingchuzhang.njugitlabapp.utilities.JSONToMapList;
import com.example.xingchuzhang.njugitlabapp.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherGroupActivity extends AppCompatActivity {

    private ListView listView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_group);
        listView=(ListView)findViewById(R.id.groupList);
        // Use the getIntent method to store the Intent that started this Activity in a variable
        Intent intent=getIntent();
        //Create an if statement to check if this Intent has the extra we passed from MainActivity
        if(intent.hasExtra(Intent.EXTRA_TEXT)) {
            String jsonStr=intent.getStringExtra(Intent.EXTRA_TEXT);
            try {
                JSONArray jsonData=new JSONArray(jsonStr);
                List<Map<String, Object>> dataList= JSONToMapList.transfGroupJSONToMapList(jsonData);
                listView.setAdapter(new MyGroupAdapter(this, dataList));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String,String> map=(HashMap<String,String>)listView.getItemAtPosition(position);
                        String groupId=map.get("id");
                        new GitlabConnectTask().execute(NetworkUtils.GROUP+"/"+groupId+"/"+NetworkUtils.STUDENTS);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class GitlabConnectTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String respStr = NetworkUtils.getResponseFromHttpUrl(params[0],null);
            return respStr;
        }

        @Override
        protected void onPostExecute(String respStr) {
            if (respStr != null && !respStr.equals("")) {
                System.out.println(respStr);
                handleOperation(respStr);
            }
        }
    }

    private void handleOperation(String respStr) {
        Class destinationActivity = TearcherStudentsActivity.class;

        Intent startChildActivityIntent = new Intent(this, destinationActivity);
        //添加字符串信息到Intent
        startChildActivityIntent.putExtra(Intent.EXTRA_TEXT,respStr);
        //启动另一个Activity
        startActivity(startChildActivityIntent);
    }

}
