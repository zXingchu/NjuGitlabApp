package com.example.xingchuzhang.njugitlabapp.studentUI;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xingchuzhang.njugitlabapp.R;
import com.example.xingchuzhang.njugitlabapp.tearcharUI.TeacherExamActivity;
import com.example.xingchuzhang.njugitlabapp.tearcharUI.TeacherExerciseActivity;
import com.example.xingchuzhang.njugitlabapp.tearcharUI.TeacherGroupActivity;
import com.example.xingchuzhang.njugitlabapp.tearcharUI.TeacherHomeActivity;
import com.example.xingchuzhang.njugitlabapp.tearcharUI.TeacherHomeworkActivity;
import com.example.xingchuzhang.njugitlabapp.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class StudentHomeActivity extends AppCompatActivity {

    private int operationflag=-1;

    private TextView userNameText=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        userNameText=(TextView)findViewById(R.id.userNameText);
        Intent intent=getIntent();
        //Create an if statement to check if this Intent has the extra we passed from MainActivity
        if(intent.hasExtra(Intent.EXTRA_TEXT)) {
            String jsonStr=intent.getStringExtra(Intent.EXTRA_TEXT);
            try {
                JSONObject jsonData=new JSONObject(jsonStr);
                userNameText.setText(jsonData.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected void stuStatistic(View v){
        operationflag=0;
        new GitlabConnectTask().execute("StuStatistic");
    }

    protected void stuHomework(View v){
        operationflag=1;
        new GitlabConnectTask().execute(NetworkUtils.COURSE+"/2/"+NetworkUtils.HOMEWORK);
    }

    protected void stuExercise(View v){
        operationflag=2;
        new GitlabConnectTask().execute(NetworkUtils.COURSE+"/2/"+NetworkUtils.EXERCISE);
    }

    protected void stuExam(View v){
        operationflag=3;
        new GitlabConnectTask().execute(NetworkUtils.COURSE+"/2/"+NetworkUtils.EXAM);
    }

    protected void startReadMe(View v){
        Class destinationActivity =  ReadMeActivity.class;
        Intent startChildActivityIntent = new Intent(this, destinationActivity);
        //启动另一个Activity
        startActivity(startChildActivityIntent);
    }

    private void handleOperation(String respStr) {
        Class destinationActivity = null;
        switch (operationflag){
            case 0:
                destinationActivity=StuStatisticActivity.class;
                break;
            case 1:
                destinationActivity=TeacherHomeworkActivity.class;
                break;
            case 2:
                destinationActivity=TeacherExerciseActivity.class;
                break;
            case 3:
                destinationActivity=TeacherExamActivity.class;
                break;
            default:
                Toast.makeText(this, "操作失败", Toast.LENGTH_LONG).show();
                return;
        }
        Intent startChildActivityIntent = new Intent(this, destinationActivity);
        //添加字符串信息到Intent
        startChildActivityIntent.putExtra(Intent.EXTRA_TEXT,respStr);
        //启动另一个Activity
        startActivity(startChildActivityIntent);
    }

    public class GitlabConnectTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String respStr=null;
            if(params[0].equals("StuStatistic")) {
                respStr= NetworkUtils.getStuStatusticString();
            }else {
                respStr= NetworkUtils.getResponseFromHttpUrl(params[0], null);
            }
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
