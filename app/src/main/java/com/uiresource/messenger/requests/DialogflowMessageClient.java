package com.uiresource.messenger.requests;

import android.os.AsyncTask;
import android.os.StrictMode;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DialogflowMessageClient extends AsyncTask {

    /**
     * Sends the post request to dialogflow, returns the text-message response.
     * @return
     */

    public DialogflowMessageClient(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public String postData(String message) {
        String json = (String) doInBackground(new String[]{message});
        try {
            JSONObject jsonObj = new JSONObject(json);
            json = jsonObj.getJSONObject("queryResult").getString("fulfillmentText");
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HttpPost getPostRequest(String message){
        HttpPost httpPost = new HttpPost(RequestConfig.requestURL);
        httpPost.addHeader("Authorization","Bearer ya29.c.Kl65B4NCpqHrqjoyQrOb0R8BiD6LIUVvrY4lQj57cCFEHOhSQ2CrPHJIMgoD-DZaPLpZ-vx3877wu3Z8F-wzsGzP1kzoH9aNzAxphzEfyTiU1Rai7SZ1_ztzhmwhiEhL");
        httpPost.addHeader("Content-Type","application/json; charset=utf-8");
        httpPost.setEntity(RequestConfig.requestEntityWithMessage(message));
        return httpPost;
    }


    @Override
    protected Object doInBackground(Object[] object) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpPost httpPost = getPostRequest((String) object[0]);
            HttpResponse httpResponse = httpclient.execute(httpPost);
            String json = EntityUtils.toString(httpResponse.getEntity());
            return json;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
