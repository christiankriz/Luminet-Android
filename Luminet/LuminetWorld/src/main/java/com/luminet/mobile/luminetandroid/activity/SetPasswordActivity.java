package com.luminet.mobile.luminetandroid.activity;

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
import android.widget.TextView;
import android.widget.Toolbar;

import com.luminet.mobile.luminetandroid.R;
import com.luminet.mobile.luminetandroid.api.MuleAPI;
import com.luminet.mobile.luminetandroid.login.Login;
import com.luminet.mobile.luminetandroid.login.RegisterUser;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;
import static com.luminet.mobile.luminetandroid.app.LuminetSplashScreen.MyPREFERENCES;

/**
 * Created by chris on 2017/10/25.
 */

public class SetPasswordActivity extends Activity {
    private final OkHttpClient client = new OkHttpClient();
    Toolbar toolbar;
    TextView screenName;
    EditText newPassword, confirmPassword;
    TextView changePasswordNumber;
    Button next;
    String userId, password, newPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        password = intent.getStringExtra("pWord");
        setContentView(R.layout.user_registration_password);
        changePasswordNumber = findViewById(R.id.label_reg_num);
        changePasswordNumber.setText(userId);
        newPassword = findViewById(R.id.new_password_text_input);
        newPassword = findViewById(R.id.new_password_text_input);
        confirmPassword = findViewById(R.id.confirm_new_password_text_input);
        next = findViewById(R.id.register_next_button);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });

    }

    private void updatePassword(){
        newPasswordText = newPassword.getText().toString();
        if(newPassword.getText().equals("") || confirmPassword.getText().equals("")){
            String message = "Please enter all the required fields";
            String title = "Change Password Error";
            displayMessage(message, title);
        }else {
            if (newPassword.getText().equals(confirmPassword.getText())) {
                if (isValidPassword(newPassword.getText().toString())) {
                    uploadNewPassword();
                }
            } else {
                String message = "New passwords not same";
                String title = "Change Password Error";
                displayMessage(message, title);
            }
        }

    }

    public void login(){

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


    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    private void uploadNewPassword(){
        String currentTime = (String) android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new Date());
        String paramData = "{\"properties\":{\"gcmId\":\"\",\"appVersion\":\"0.5.28.125\",\"status\":2,\"updateDate\":\"" + currentTime + "\",\"password\":\"" + newPasswordText + "\"},\"nodeLabel\":\"Consumer\",\"nodeKey\":\"cell\",\"nodeKeyValue\":\"" + userId + "\"}\"";
        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("params", paramData);
        RequestBody formBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(MuleAPI.setPasswordUrl)
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
                        Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                        intent.putExtra("userId", userId);
                        //intent.putExtra("pWord", codeNumberString);
                        startActivity(intent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.v(TAG, "Check Log " + respondData);
            }

            @Override
            public void onDataFailed(Call call, IOException e){
                String message = "We could not connect to Luminet World please check you internet connectivity and try again";
                String title = "Register Error";
                displayMessage(message, title);
                Log.v(TAG, "Registration Exception : ", e);
            }
        });


    }

    private void displayMessage(final String message, final String title){

        android.support.v7.app.AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new android.support.v7.app.AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new android.support.v7.app.AlertDialog.Builder(this);
        }
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}

