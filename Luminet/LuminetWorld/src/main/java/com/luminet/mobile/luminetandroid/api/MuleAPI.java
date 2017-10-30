package com.luminet.mobile.luminetandroid.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luminet.mobile.luminetandroid.adapter.NewsFeedAdapter;
import com.luminet.mobile.luminetandroid.newsFeed.NewsFeed;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by chris on 2017/10/12.
 */

public class MuleAPI {

    private static String url = "http://196.223.97.152";
    public static String newsFeedsUrl = url + ":11106/getlatestnewsfeedbyconsumer?limit=12&page=0";
    public static String registerUserUrl = url + ":11013/createuniquenode";
    public static String requestSmsUrl = url + ":11057/sms";
    public  static String smsVerificationUrl = url + ":13001/getnodebyindex";
    public static String userLoginUrl = url + ":13001/getnodebyindex";
    public static String requestPasswordResetUrl = url + ":11051/retrievepassword";
    public static String getEnterpriseList = url + ":11002/getallenterprises";
    public static String setPasswordUrl = url + ":11101/addnodeproperties";

    public interface DataListener {
        void onDataSucceeded(Call call, Response response);
        void onDataFailed(Call call, IOException e);
    }

    public void getData(Call call, final DataListener dataListener){
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                dataListener.onDataFailed(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dataListener.onDataSucceeded(call, response);
            }
        });

    }

}
