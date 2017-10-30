package com.luminet.mobile.luminetandroid.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.luminet.mobile.luminetandroid.R;
import com.luminet.mobile.luminetandroid.api.MuleAPI;
import com.luminet.mobile.luminetandroid.login.UserVerification;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by chris on 2017/10/25.
 */

public class RegisterActivity  extends Activity {
    private final OkHttpClient client = new OkHttpClient();
    Toolbar toolbar;
    TextView screenName;
    EditText firstName, lastName, displayName;
    Button registerButton;
    String userId, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        setContentView(R.layout.update_name_surname_display);
        firstName = findViewById(R.id.first_name_text);
        lastName = findViewById(R.id.last_name_text);
        displayName = findViewById(R.id.display_name_text);
        registerButton = findViewById(R.id.register_next_button);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String message = "I accept the Luminet World Terms and Conditions";
                String title = "T's &amp; C's";
                displayMessage(message, title);
            }
        });

    }

    public void registerUser(){

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("cell", userId)
                .add("password", password);
        RequestBody formBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(MuleAPI.userLoginUrl)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        MuleAPI muleAPI = new MuleAPI();
        muleAPI.getData(call, new MuleAPI.DataListener(){

            @Override
            public void onDataSucceeded(Call call, Response response){
                String respondData = null;
                try {
                    respondData = response.body().string();
                    if (response.isSuccessful()) {
                        if(respondData.equals("\"2\"") || respondData.equals("\"3\"")){

                        } else{
                            String message = "Your Mobile Number or Password is incorrect. Please try again.";
                            String title = "Error Login";
                            displayMessage(message, title);
                        }
                    }
                    //}
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onDataFailed(Call call, IOException e){
                String message = "We could not connect to Luminet World please check you internet connectivity and try again";
                String title = "Error Registration";
                displayMessage(message, title);
                Log.v(TAG, "SMS Exception : ", e);
            }

        });
    }

    private void displayMessage(final String message, final String title){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(RegisterActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(RegisterActivity.this);
                }
                builder.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("I Accept", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                registerUser();
                            }
                        })
                        .setNegativeButton("I Do Not Accept", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

    }

}
