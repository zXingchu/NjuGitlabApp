package com.example.xingchuzhang.njugitlabapp.studentUI;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.xingchuzhang.njugitlabapp.R;
import com.example.xingchuzhang.njugitlabapp.adapter.MyExpandableListAdapter;
import com.example.xingchuzhang.njugitlabapp.utilities.JSONToMapList;
import com.example.xingchuzhang.njugitlabapp.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StuAnalysisActivity extends AppCompatActivity {

    private TextView assignIdText=null;

    private TextView stuIdText=null;

    private ExpandableListView expandableListView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_analysis);

        assignIdText=(TextView)findViewById(R.id.assignIdText);
        stuIdText=(TextView)findViewById(R.id.stuIdText);
        stuIdText.setText(NetworkUtils.STUID);
        Intent intent=getIntent();
        if(intent.hasExtra(Intent.EXTRA_TEXT)) {
            String jsonStr=intent.getStringExtra(Intent.EXTRA_TEXT);
            try {
                final JSONObject jsonData=new JSONObject(jsonStr);
                assignIdText.setText(jsonData.getString("assignmentId"));
                List<Map<String, Object>> groupData= JSONToMapList.tAnalysisJSONToMapList(jsonData.getJSONArray("questionResults"));
                ArrayList<List<Map<String, Object>>> childData=JSONToMapList.tAnalysisJSONToMapLList(jsonData.getJSONArray("questionResults"));
                expandableListView = (ExpandableListView)findViewById(R.id.expendlist);
                expandableListView.setGroupIndicator(null);
                MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,groupData,childData);
                expandableListView.setAdapter(adapter);
                expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        if(NetworkUtils.ISSTUDENT) {
                            try {
                                new GitlabConnectTask().execute("/assignment/" + jsonData.getString("assignmentId") + "/student/"
                                        + NetworkUtils.STUID + "/question/"
                                        + jsonData.getJSONArray("questionResults")
                                        .getJSONObject(childPosition)
                                        .getString("questionId"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        return false;
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void handleOperation(String respStr) {

    }

    public class GitlabConnectTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String respStr= NetworkUtils.getResponseFromHttpUrl(params[0], null);
            return respStr;
        }

        @Override
        protected void onPostExecute(String respStr) {
            if (respStr != null) {
//                handleOperation(respStr);
                System.out.println(respStr);
            }
        }
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
