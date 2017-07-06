package com.example.xingchuzhang.njugitlabapp.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xingchu Zhang on 2017/6/29 0029.
 */

public class JSONToMapList {

    public static List<Map<String, Object>> transformateJSONToMapList(JSONArray jsonArray){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();

        for(int i=0;i<jsonArray.length();i++){
            try {
                JSONObject jo = jsonArray.getJSONObject(i);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", jo.getString("id"));
                map.put("title", jo.getString("title"));
                map.put("description", jo.getString("description"));
                map.put("startAt", jo.getString("startAt"));
                map.put("endAt", jo.getString("endAt"));
                map.put("questions", jo.getJSONArray("questions"));
                map.put("course", jo.getString("course"));
                map.put("status", jo.getString("status"));
                list.add(map);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static List<Map<String, Object>> transfGroupJSONToMapList(JSONArray jsonArray){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for(int i=0;i<jsonArray.length();i++){
            try {
                JSONObject jo = jsonArray.getJSONObject(i);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", jo.getString("id"));
                map.put("name", jo.getString("name"));
                list.add(map);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static List<Map<String,Object>> tStudentsJSONToMapList(JSONArray jsonArray) {
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for(int i=0;i<jsonArray.length();i++){
            try {
                JSONObject jo = jsonArray.getJSONObject(i);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", jo.getString("id"));
                map.put("username", jo.getString("username"));
                map.put("name", jo.getString("name"));
                map.put("type", jo.getString("type"));
                map.put("gender", jo.getString("gender"));
                map.put("email", jo.getString("email"));
                map.put("gitId", jo.getString("gitId"));
                map.put("groupId", jo.getString("groupId"));
                map.put("gitUsername", jo.getString("gitUsername"));
                list.add(map);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static List<Map<String,Object>> tScoreJSONToMapList(JSONObject jsonObject) {
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        try {
            JSONArray jsonArray=jsonObject.getJSONArray("questions");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject questionJSON = jsonArray.getJSONObject(i).getJSONObject("questionInfo");
                JSONArray stuArray=jsonArray.getJSONObject(i).getJSONArray("students");
                int[] sectionNumber=setSectionNumber(stuArray);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", questionJSON.getString("id"));
                map.put("title", questionJSON.getString("title"));
                map.put("description", questionJSON.getString("description"));
                map.put("type", questionJSON.getString("type"));
                map.put("sectionNumber",sectionNumber);
                list.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static int[] setSectionNumber(JSONArray stuArray){
        int[] sectionNumber={0,0,0,0,0,0,0,0,0,0,0};
        int score=0;
        for(int i=0;i<stuArray.length();i++){
            try {
                score=stuArray.getJSONObject(i).getInt("score");
                sectionNumber[score/10]++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return sectionNumber;
    }

    public static List<Map<String,Object>> tAnalysisJSONToMapList(JSONArray jsonArray) {
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for(int i=0;i<jsonArray.length();i++){
            try {
                JSONObject jo = jsonArray.getJSONObject(i);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("questionId", jo.getString("questionId"));
                map.put("questionTitle", jo.getString("questionTitle"));
                map.put("compiled", jo.getJSONObject("testResult").getString("compile_succeeded"));
                map.put("tested", jo.getJSONObject("testResult").getString("tested"));
                map.put("score", jo.getJSONObject("scoreResult").getString("score"));
                list.add(map);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static ArrayList<List<Map<String,Object>>> tAnalysisJSONToMapLList(JSONArray jsonArray) {
        ArrayList<List<Map<String,Object>>> lList=new ArrayList<List<Map<String,Object>>>();
        for(int i=0;i<jsonArray.length();i++){
            List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
            try {
                if(jsonArray.getJSONObject(i).getJSONObject("testResult").getString("testcases")==null) {
                    JSONArray testArray = jsonArray.getJSONObject(i).getJSONObject("testResult").getJSONArray("testcases");
                    for (int j = 0; j < testArray.length(); j++) {
                        JSONObject jo = testArray.getJSONObject(i);
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("name", jo.getString("name"));
                        map.put("passed", jo.getString("passed"));
                        list.add(map);
                    }
                }
                lList.add(list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return lList;
    }

}
