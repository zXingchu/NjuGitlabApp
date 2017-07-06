/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.xingchuzhang.njugitlabapp.utilities;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    final static String GITLAB_BASE_URL = "http://115.29.184.56:8090/api/";

    public static String USERNAME;
    public static String USERPWD;

    public final static String GITLAB_LOGIN = "user/auth";

    public final static String GROUP = "group";
    public final static String EXAM = "exam";
    public final static String HOMEWORK = "homework";
    public final static String EXERCISE = "exercise";

    public final static String STUDENTS = "students";

    public final static String COURSE = "course";

    public final static String ASSIGNMENT = "assignment";
    public final static String ASSIGNMENT_ID = "assignmentId";

    public final static String SCORE = "score";

    public final static String STUDENT = "student";

    public final static String ANALYSIS = "analysis";

    public static boolean ISSTUDENT=false;
    public static String STUID="0";

    public static URL buildUrl(String segment) {
        Uri builtUri = Uri.parse(GITLAB_BASE_URL+segment).buildUpon().build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getStuStatusticString(){
        JSONArray reArray=new JSONArray();
        try {
            String analysisUrlStr=NetworkUtils.STUDENT+"/"+NetworkUtils.STUID+"/"+NetworkUtils.ANALYSIS;
            JSONArray hArray=new JSONArray(
                    getResponseFromHttpUrl(NetworkUtils.COURSE+"/2/"+NetworkUtils.HOMEWORK,null));

            for(int i=0;i<hArray.length();i++){
//                JSONObject jsonObject=new JSONObject();
                JSONObject tempJSON=new JSONObject(getResponseFromHttpUrl(NetworkUtils.ASSIGNMENT +"/"
                        +hArray.getJSONObject(i).getString("id")
                        +"/" +analysisUrlStr,null));
//                jsonObject.put("id",tempJSON.getString("assignmentId"));
//                tempArray=tempJSON.getJSONArray("questionResults");
//                for (int j = 0; j < tempArray.length(); j++) {
//                    jsonObject.put("questionId",tempArray.getJSONObject(i).get("questionId"));
//                    jsonObject.put("score",tempArray.getJSONObject(i).get("questionId"));
//                }
                tempJSON.put("type","homework");
                System.out.println(tempJSON.toString());
                reArray.put(tempJSON);
            }

            JSONArray eArray=new JSONArray(
                    getResponseFromHttpUrl(NetworkUtils.COURSE+"/2/"+NetworkUtils.EXERCISE,null));
            for(int i=0;i<eArray.length();i++){
                JSONObject tempJSON=new JSONObject(getResponseFromHttpUrl(NetworkUtils.ASSIGNMENT +"/"
                        +eArray.getJSONObject(i).getString("id")
                        +"/" +analysisUrlStr,null));
                tempJSON.put("type","exercise");
                System.out.println(tempJSON.toString());
                reArray.put(tempJSON);
            }

            JSONArray examArray=new JSONArray(
                    getResponseFromHttpUrl(NetworkUtils.COURSE+"/2/"+NetworkUtils.EXAM,null));
            for(int i=0;i<examArray.length();i++){
                JSONObject tempJSON=new JSONObject(getResponseFromHttpUrl(NetworkUtils.ASSIGNMENT +"/"
                        +examArray.getJSONObject(i).getString("id")
                        +"/" +analysisUrlStr,null));
                tempJSON.put("type","exam");
                System.out.println(tempJSON.toString());
                reArray.put(tempJSON);
            }
            JSONObject tempJSON=new JSONObject(getResponseFromHttpUrl(NetworkUtils.ASSIGNMENT +"/"
                    +"38"
                    +"/" +NetworkUtils.STUDENT+"/256/"+NetworkUtils.ANALYSIS,null));
            tempJSON.put("type","exam");
            System.out.println(tempJSON.toString());
            reArray.put(tempJSON);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reArray.toString();
    }

    public static String getResponseFromHttpUrl(String urlstr,String jsonStr){
        URL url=buildUrl(urlstr);
        HttpURLConnection urlConnection = null;
        BufferedReader br=null;
        try {
            urlConnection=(HttpURLConnection) url.openConnection();
            /* optional request header */
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            /* optional request header */
            urlConnection.setRequestProperty("Accept", "application/json");
            // read response
            /* for Get request */
            if(jsonStr!=null) {
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                // String jsonString = gson.toJson(dto);
                wr.writeBytes(jsonStr);
                wr.flush();
                wr.close();
            }else {
                //将base64信息转换为string
                String tokenStr = Base64.encodeToString((USERNAME+":"+USERPWD).getBytes(),Base64.DEFAULT);
                //Basic YFUDIBGDJHFK78HFJDHF==    token的格式
                String token = "Basic "+tokenStr;
                //把认证信息发到header中
                urlConnection.setRequestProperty("Authorization", token);
            }
            // try to get response
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200||jsonStr==null) {
                br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String str;
                while((str=br.readLine())!=null){
                    return str;
                }
            }
        } catch (ProtocolException e) {
            return null;
        } catch (IOException e) {
            return null;
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }
}