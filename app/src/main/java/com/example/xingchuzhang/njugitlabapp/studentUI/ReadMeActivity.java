package com.example.xingchuzhang.njugitlabapp.studentUI;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xingchuzhang.njugitlabapp.R;
import com.example.xingchuzhang.njugitlabapp.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReadMeActivity extends AppCompatActivity {

    private EditText stuAssignEdit=null;
    private EditText stuQuestionEdit=null;
    private TextView textView4=null;
    private TextView readMeText=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_me);

        stuAssignEdit=(EditText)findViewById(R.id.stuAssignEdit);
        stuQuestionEdit=(EditText)findViewById(R.id.stuQuestionEdit);
        textView4=(TextView)findViewById(R.id.textView4);
        readMeText=(TextView)findViewById(R.id.readMeText);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void setReadMeText(View v){
        if(TextUtils.isEmpty(stuAssignEdit.getText())) {
            Toast.makeText(this, "请输入Assignemt ID", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(stuQuestionEdit.getText())) {
            Toast.makeText(this, "请输入Question ID", Toast.LENGTH_LONG).show();
            return;
        }
        new GitlabConnectTask().execute("assignment/" + stuAssignEdit.getText().toString() + "/student/"
                + "227" + "/question/"
                + stuQuestionEdit.getText().toString());
    }

    public class GitlabConnectTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String respStr= NetworkUtils.getResponseFromHttpUrl(params[0], null);
            return respStr;
        }

        @Override
        protected void onPostExecute(String respStr) {
            if(respStr==null) {
                textView4.setVisibility(View.INVISIBLE);
                readMeText.setText("");
                return;

            }
            textView4.setVisibility(View.VISIBLE);
            try {
                JSONObject jsonObject=new JSONObject(respStr);
                readMeText.setText(jsonObject.getString("content"));
            } catch (JSONException e) {
                e.printStackTrace();
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
