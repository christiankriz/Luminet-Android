package com.luminet.mobile.luminetandroid.app;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.luminet.mobile.luminetandroid.api.MuleAPI;
import com.luminet.mobile.luminetandroid.data.EnterpriseDTO;
import com.luminet.mobile.luminetandroid.login.LoginMain;
import com.luminet.mobile.luminetandroid.realm.RealmController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.internal.Table;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;


/**
 * Created by chris on 2017/10/20.
 */

public class Luminet extends Application {
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void onCreate() {
        super.onCreate();
       //Config Realm for the application
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        Realm realm = Realm.getDefaultInstance();
        getEnterprises();
        Intent intent = new Intent(Luminet.this, LuminetSplashScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void getEnterprises(){
        FormBody.Builder formBuilder = new FormBody.Builder();
        RequestBody formBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(MuleAPI.getEnterpriseList)
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
                       addEenterprise(jsonData);
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

    public void addEenterprise(String jsonString){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        String id;
        JSONArray array = null;
        try {
            array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject row = array.getJSONObject(i);
                JsonParser parser = new JsonParser();
                JsonElement mJson =  parser.parse(String.valueOf(row));
                Gson gson = new Gson();
                EnterpriseDTO object = gson.fromJson(mJson, EnterpriseDTO.class);
                id = object.getId();
                if (realm.where(EnterpriseDTO.class).equalTo("id", id).count() == 0) {
                    // there are no object with this `Id`
                    realm.copyToRealmOrUpdate(object);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        realm.commitTransaction();
    }

    private void displayMessage(final String message, final String title, final String response) {

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(Luminet.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(Luminet.this);
        }
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (title.equals("Success Registration")) {
                            Intent i = new Intent(getBaseContext(), LoginMain.class);
                            startActivity(i);
                        }
                    }
                })
                .show();

    }
}