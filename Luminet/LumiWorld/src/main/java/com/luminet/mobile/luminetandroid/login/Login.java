package com.luminet.mobile.luminetandroid.login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.luminet.mobile.luminetandroid.activity.LuminetActivity;
import com.luminet.mobile.luminetandroid.R;
import com.luminet.mobile.luminetandroid.api.DataService;
import com.luminet.mobile.luminetandroid.api.MuleAPI;
import com.luminet.mobile.luminetandroid.app.LuminetSplashScreen;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;
import static com.luminet.mobile.luminetandroid.login.RegisterUser.MyPREFERENCES;

/**
 * Created by chris on 2017/10/11.
 */

public class Login extends Activity {
    private final OkHttpClient client = new OkHttpClient();
    SharedPreferences sharedpreferences;
    EditText userNameText;
    EditText passwordText;
    Button login;
    String userNumber;
    String password;
    String numberEntered;
    String passwordEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        userNameText = findViewById(R.id.login_phone_number);
        passwordText = findViewById(R.id.login_password);
        login = findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean valiedNum = validateNumber();
                if(valiedNum){
                    passwordEntered = passwordText.getText().toString();
                    login();
                }
            }
        });

    }

    public void login(){

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("cell", numberEntered)
                .add("password", passwordEntered);
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
                        /*if(respondData.equals("\"2\"")){
                            String message = "Please take the time to complete the registration process.";
                            String title = "Error Login";
                            displayMessage(message, title, "error");

                        }else if(respondData.equals("\"2\"") || respondData.equals("\"3\"")){ */
                            if(respondData.equals("\"2\"") || respondData.equals("\"3\"")){
                                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("useId", numberEntered);
                                editor.putString("pWord", passwordEntered);
                                editor.commit();

                                DataService dataService = new DataService(Login.this);
                                dataService.getNodeByRelationship();

                                Intent intent = new Intent(Login.this, LuminetActivity.class);
                                intent.putExtra("userId", numberEntered);
                                startActivity(intent);
                            } else{
                                String message = "Your Mobile Number or Password is incorrect. Please try again.";
                                String title = "Error Login";
                                displayMessage(message, title, "error");
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
                displayMessage(message, title, "error");
                Log.v(TAG, "SMS Exception : ", e);
            }

        });
    }

    private boolean validateNumber(){
        boolean valied = false;
        numberEntered = userNameText.getText().toString();
        if(numberEntered.length() != 10){
            String message = "Please enter 10 digit number";
            String title = "Register";
            displayMessage(message, title, "error");
        }else {
            valied = true;
            numberEntered = numberEntered.substring(1);
            numberEntered = "27" + numberEntered;
        }

        return  valied;
    }

    private void displayMessage(final String message, final String title, final String response){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                android.support.v7.app.AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new android.support.v7.app.AlertDialog.Builder(Login.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new android.support.v7.app.AlertDialog.Builder(Login.this);
                }
                builder.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

            }
        });

    }
}
