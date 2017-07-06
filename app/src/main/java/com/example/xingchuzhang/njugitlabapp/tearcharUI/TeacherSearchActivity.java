package com.example.xingchuzhang.njugitlabapp.tearcharUI;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xingchuzhang.njugitlabapp.R;
import com.example.xingchuzhang.njugitlabapp.studentUI.StuAnalysisActivity;
import com.example.xingchuzhang.njugitlabapp.utilities.NetworkUtils;

public class TeacherSearchActivity extends AppCompatActivity {

    EditText assignIdEdit=null;

    EditText stuIdEdit=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_search);
        assignIdEdit=(EditText)findViewById(R.id.assignIdEdit);
        stuIdEdit=(EditText)findViewById(R.id.stuIdEdit);
    }


    protected void searchTestCase(View v){
        if(TextUtils.isEmpty(assignIdEdit.getText())) {
            Toast.makeText(this, "请输入Assignemt ID", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(stuIdEdit.getText())) {
            Toast.makeText(this, "请输入Student ID", Toast.LENGTH_LONG).show();
            return;
        }
        String assignId=assignIdEdit.getText().toString();
        NetworkUtils.STUID=stuIdEdit.getText().toString();
        new GitlabConnectTask().execute(NetworkUtils.ASSIGNMENT+"/"+assignId+"/"
                +NetworkUtils.STUDENT+"/"+NetworkUtils.STUID+"/"+NetworkUtils.ANALYSIS);
    }
    private void handleOperation(String respStr) {
        Class destinationActivity = StuAnalysisActivity.class;
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
