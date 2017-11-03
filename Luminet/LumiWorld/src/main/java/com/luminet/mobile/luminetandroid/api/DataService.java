package com.luminet.mobile.luminetandroid.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.luminet.mobile.luminetandroid.data.EnterpriseDTO;
import com.luminet.mobile.luminetandroid.data.ServicesDTO;
import com.luminet.mobile.luminetandroid.realm.DataReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.realm.Realm;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;
import static com.luminet.mobile.luminetandroid.app.LuminetSplashScreen.MyPREFERENCES;

/**
 * Created by chris on 2017/11/01.
 */

public class DataService {
    private final OkHttpClient client = new OkHttpClient();
    public DataReceiver dataReceiver;
    SharedPreferences sharedpreferences;
    Context context;

    public DataService(Context context){
        this.dataReceiver = new DataReceiver();
        this.context = context;
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public void getGetAllEnterprise(){
        FormBody.Builder formBuilder = new FormBody.Builder();
        RequestBody formBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(MuleAPI.Lumi.GETENTERPRISE.url())
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, "Check Log " + jsonData);
                    if (response.isSuccessful()) {
                        dataReceiver.addEnterprise(jsonData);
                    } else {
                        String message = "Could not get Enterprise List";
                        String title = "Data Download";
                        //displayMessage(message, title, "error");
                    }
                } catch (IOException e) {
                    Log.v(TAG, "Exception caught : ", e);
                }
            }
        });
    }

    public void getServices(){
        FormBody.Builder formBuilder = new FormBody.Builder();
        RequestBody formBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(MuleAPI.getAllServicesUrl)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, "Check Log " + jsonData);
                    if (response.isSuccessful()) {
                        dataReceiver.addServices(jsonData);
                    } else {
                        String message = "Could not get Service List";
                        String title = "Data Download";
                        //displayMessage(message, title, "error");
                    }
                } catch (IOException e) {
                    Log.v(TAG, "Exception caught : ", e);
                }
            }
        });

    }

    public void getAllEnterpriseService(){
        FormBody.Builder formBuilder = new FormBody.Builder();
        RequestBody formBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(MuleAPI.getAllEnterpriseService)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, "Check Log " + jsonData);
                    if (response.isSuccessful()) {
                        dataReceiver.addAllEnterpriseService(jsonData);
                    } else {
                        String message = "Could not get Service List";
                        String title = "Data Download";
                        //displayMessage(message, title, "error");
                    }
                } catch (IOException e) {
                    Log.v(TAG, "Exception caught : ", e);
                }
            }
        });
    }

    public void getNodeByRelationship(){
        String userId, password;
        userId = sharedpreferences.getString("useId", null);
        password = sharedpreferences.getString("pWord", null);

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("params", "{\"cell \":\"" + userId + "\"}");
        RequestBody formBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(MuleAPI.getNodeByRelationship)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, "Check Log " + jsonData);
                    if (response.isSuccessful()) {
                        dataReceiver.addNodeByRelationship(jsonData);
                    } else {
                        String message = "Could not get Service List";
                        String title = "Data Download";
                        //displayMessage(message, title, "error");
                    }
                } catch (IOException e) {
                    Log.v(TAG, "Exception caught : ", e);
                }
            }
        });
    }


}
