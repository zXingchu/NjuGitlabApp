package com.example.xingchuzhang.njugitlabapp.tearcharUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xingchuzhang.njugitlabapp.R;
import com.example.xingchuzhang.njugitlabapp.adapter.MyAdapter;
import com.example.xingchuzhang.njugitlabapp.adapter.MyStudentsAdapter;
import com.example.xingchuzhang.njugitlabapp.utilities.JSONToMapList;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TearcherStudentsActivity extends AppCompatActivity {

    private ListView listView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_students);
        listView=(ListView)findViewById(R.id.studentList);
        // Use the getIntent method to store the Intent that started this Activity in a variable
        Intent intent=getIntent();
        //Create an if statement to check if this Intent has the extra we passed from MainActivity
        if(intent.hasExtra(Intent.EXTRA_TEXT)) {
            String jsonStr=intent.getStringExtra(Intent.EXTRA_TEXT);
            try {
                JSONArray jsonData=new JSONArray(jsonStr);
                List<Map<String, Object>> dataList= JSONToMapList.tStudentsJSONToMapList(jsonData);
                listView.setAdapter(new MyStudentsAdapter(this, dataList));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String,String> map=(HashMap<String,String>)listView.getItemAtPosition(position);
                        String name=map.get("name");
                        String sex=map.get("gender");
                        Toast.makeText(getApplicationContext(),
                                "你选择了第"+position+"个Item，姓名："+name+" 性别:"+sex,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
