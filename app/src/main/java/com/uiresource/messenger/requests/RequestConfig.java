package com.uiresource.messenger.requests;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

class RequestConfig {
    final static String requestURL = "https://dialogflow.googleapis.com/v2/projects/my-project-1575962498613/agent/sessions/28365186-6638-556d-0f53-830905e65707:detectIntent";

    public static StringEntity requestEntityWithMessage(String message){
        try {
            return new StringEntity("{\"queryInput\":{\"text\":{\"text\":\"" + message + "\",\"languageCode\":\"de\"}},\"queryParams\":{\"timeZone\":\"Europe/Berlin\"}}") ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
