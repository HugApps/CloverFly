package com.cloverfly.cloverfly.cloverfly;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
/**
 * Created by Hugo on 7/22/2015.
 *
 * // Majority of code taken from http://www.learn2crack.com/2014/04/android-login-registration-nodejs-client.html/2 , similar practices of getting json and pasing json is common method
 */
public class NodeConnect {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";


    public NodeConnect() {

    }

    public NodeConnect(JSONObject j){
        jObj = j ;
    }


    public JSONObject getJSONFromUrl(String url, List<NameValuePair> params) {


        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));


            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            is.close();
            json = sb.toString();
           // System.out.println(json);
            //Log.e("JSON", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }


        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }


        return jObj;

    }




    public boolean PassJson (String url ,JSONObject jsonobj) {
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppostreq = new HttpPost(url);
            StringEntity se = new StringEntity(jsonobj.toString());
            se.setContentType("application/json;charset=UTF-8");
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
            httppostreq.setEntity(se);
            HttpResponse httpresponse = httpclient.execute(httppostreq);
            System.out.println(httpresponse.toString());
            return true;
        } catch( Exception E){E.printStackTrace();return false;}


    }


    public JSONObject getJSON(String url, JSONObject obj) {


        synctask myTask = new synctask();
        try{

            jobj= myTask.execute(obj);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        return jobj;
    }



























    JSONObject jobj;
    public JSONObject getJSON(String url, List<NameValuePair> params) {

        Params param = new Params(url,params);
        Request myTask = new Request();
        try{

            jobj= myTask.execute(param).get();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        return jobj;
    }





    private static class Params {
        String url;
        List<NameValuePair> params;


        Params(String url, List<NameValuePair> params) {
            this.url = url;
            this.params = params;

        }
    }

    private class Request extends AsyncTask<Params, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(Params... args) {

            NodeConnect request = new NodeConnect();
            JSONObject json = request.getJSONFromUrl(args[0].url,args[0].params);

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            super.onPostExecute(json);

        }

    }





   private class synctask extends AsyncTask<JSONObject, String, JSONObject>{

       @Override
       protected JSONObject doInBackground(JSONObject... args) {

           NodeConnect request = new NodeConnect();


           return json;
       }
       @Override
       protected void onPostExecute(JSONObject json) {

           super.onPostExecute(json);

       }

    }




}
