package com.example.xingchuzhang.njugitlabapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xingchuzhang.njugitlabapp.studentUI.StudentHomeActivity;
import com.example.xingchuzhang.njugitlabapp.tearcharUI.TeacherHomeActivity;
import com.example.xingchuzhang.njugitlabapp.utilities.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText userNameText=null;

    EditText userPwdText=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameText=(EditText)findViewById(R.id.UserNameText);
        userPwdText=(EditText)findViewById(R.id.UserPwdText);

    }

    protected void loginIn(View v){
        if(TextUtils.isEmpty(userNameText.getText())) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(userPwdText.getText())) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_LONG).show();
            return;
        }
        String name=userNameText.getText().toString();
        String pwd=userPwdText.getText().toString();
        NetworkUtils.USERNAME=name;
        NetworkUtils.USERPWD=pwd;
//        Toast.makeText(this, "Name:"+name+"密码"+pwd, Toast.LENGTH_LONG).show();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("username",name);
            jsonObject.put("password",pwd);
            System.out.println(jsonObject);
            String jsonStr=jsonObject.toString();
//            String respLogin=NetworkUtils.getResponseFromHttpUrl(url,jsonObject.toString());
            new GitlabConnectTask().execute(NetworkUtils.GITLAB_LOGIN,jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    protected void exitApp(View v){
        System.exit(0);
    }

    private void handleRespStr(String respStr){
        try {
            Context context = MainActivity.this;
            Class destinationActivity = null;
            JSONObject respJOSN=new JSONObject(respStr);
            if(!respJOSN.has("type")){
                Toast.makeText(this, "用户名或密码错误，请重试", Toast.LENGTH_LONG).show();
                return ;
            }
            String type=respJOSN.getString("type");
            Toast.makeText(this, type, Toast.LENGTH_LONG).show();
            if(type.equals("teacher")){
                destinationActivity = TeacherHomeActivity.class;
                NetworkUtils.ISSTUDENT=false;
            }else if(type.equals("student")){
                destinationActivity = StudentHomeActivity.class;
                NetworkUtils.ISSTUDENT=true;
                NetworkUtils.STUID=respJOSN.getString("id");
            }else {
                Toast.makeText(this, "暂不支持管理员登陆", Toast.LENGTH_LONG).show();
                return;
            }
            Intent startChildActivityIntent = new Intent(context, destinationActivity);
            //添加字符串信息到Intent
            startChildActivityIntent.putExtra(Intent.EXTRA_TEXT,respStr);
            startChildActivityIntent.putExtra("UserId",respJOSN.getString("id"));
            startActivity(startChildActivityIntent);
        } catch (JSONException e) {
            Toast.makeText(this, "登陆失败,请重试", Toast.LENGTH_LONG).show();
        }
    }

    public class GitlabConnectTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String respLogin = NetworkUtils.getResponseFromHttpUrl(params[0],params[1]);
            return respLogin;
        }

        @Override
        protected void onPostExecute(String respLogin) {
            if (respLogin != null && !respLogin.equals("")) {
                System.out.println(respLogin);
                handleRespStr(respLogin);
            }
        }
    }

}
