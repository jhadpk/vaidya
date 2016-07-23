package com.inmobi.app.utils;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.inmobi.app.model.OrganizationData;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//import org.apache.http.impl.client.HttpClientBuilder;
/**
 * Created by krishna.tiwari on 15/08/15.
 */
public class MorphWebServiceUtility {

    public static boolean saveOrgDetails(OrganizationData organizationData) {

        String url = "http://10.14.121.33:8081/MorphWebService/morph/save/org";
        HttpResponse response;

        try {

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF8");
            StringEntity se = new StringEntity(organizationData.toString());
            post.setEntity(se);
            response = client.execute(post);
            Log.d("Krishna response :: ", response.toString());
        } catch (Exception e) {
            e.printStackTrace();

        }
        return true;
    }

    public boolean test(OrganizationData organizationData) {

        Log.d("Krihna", "test called");
        String url = "http://10.14.121.33:8081/MorphWebService/morph/org";
     //   sendPostRequest(organizationData);
//        HttpResponse response;
//
//        try {
//
//            HttpClient client = new DefaultHttpClient();
//            HttpPost post = new HttpPost(url);
//            post.setHeader("Accept", "application/json");
//            post.setHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF8");
//            response = client.execute(post);
//            Log.d("Krishna response :: ", response.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
        return true;
    }

//    private void sendPostRequest(final OrganizationData organizationData) {
//
//        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
//
//            @Override
//            protected String doInBackground(String... params) {
//
//                HttpClient httpClient = HttpClientBuilder.create().build();
//                String url = "http://10.14.121.33:8081/MorphWebService/morph/org";
//                try {
//                    Log.d("Krishna", "Reacged");
//                    JSONObject org_data = new JSONObject();
//                    org_data.put("name", organizationData.getName());
//                    org_data.put("phone", organizationData.getPhone());
//                    org_data.put("city", organizationData.getCity());
//                    org_data.put("address", organizationData.getAddress());
//                    org_data.put("email", organizationData.getEmail());
//                    org_data.put("password", organizationData.getPassword());
//
//                    HttpPost post = new HttpPost(url);
//
//
//                    StringEntity stringEntity = new StringEntity(org_data.toString());
//
//                    post.setEntity(stringEntity);
//                    post.setHeader("Content-type", "application/json");
//                    HttpResponse result = httpClient.execute(post);
//                    if (result.getStatusLine().getStatusCode() == 200) {
//                        Log.d("krishna", "Done job");
//                        System.out.println("Done");
//                    } else {
//                        System.out.println("Something wrong");
//                        Log.d("krishna", "Not able to do that sorry");
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                super.onPostExecute(result);
//
//                if (result.equals("working")) {
//
//                } else {
//
//                }
//            }
//        }
//        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
//        sendPostReqAsyncTask.execute(organizationData.getName(), organizationData.getPassword());
//    }
}
