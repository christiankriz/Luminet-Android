package com.luminet.mobile.luminetandroid.login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.luminet.mobile.luminetandroid.R;
import com.luminet.mobile.luminetandroid.activity.SetPasswordActivity;
import com.luminet.mobile.luminetandroid.api.MuleAPI;
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
 * Created by chris on 2017/10/13.
 */

public class UserVerification extends Activity {
    private final OkHttpClient client = new OkHttpClient();
    EditText codeNumberEdit;
    TextView numberLabel;
    Button verifyCode;
    Button resendCode;
    String userId;
    SharedPreferences sharedpreferences;
    String codeNumberString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_vefification);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        codeNumberEdit = findViewById(R.id.sms_code_text_input);
        numberLabel = findViewById(R.id.sms_verification_num);
        verifyCode = findViewById(R.id.verify_button);
        numberLabel.setText(userId);
        numberLabel.setTypeface(null, Typeface.BOLD);
        resendCode = findViewById(R.id.resend_sms_button);
        resendCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                verifyCode(v);
            }
        });
        verifyCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                verifyCode(v);
            }
        });

    }

    private  void verifyCode(View v){
        codeNumberString = codeNumberEdit.getText().toString();
        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("cell", userId)
                .add("password", codeNumberString);
        RequestBody formBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(MuleAPI.smsVerificationUrl)
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
                        if(respondData.equals("\"2\"")){
                            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("userId", userId);
                            editor.putString("pWord", codeNumberString);
                            editor.commit();
                            String message = "You have been registered successfully, you can reset password";
                            String title = "Success Registration";
                            displayMessage(message, title, "");
                        }else{
                            String message = "The SMS Validation code you entered is incorrect. Please try again.";
                            String title = "Error Registration";
                            displayMessage(message, title, "error");
                        }
                    }
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

    private void displayMessage(final String message, final String title, final String response){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(UserVerification.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(UserVerification.this);
                }
                builder.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(title.equals("Success Registration")){
                                    Intent intent = new Intent(getBaseContext(), SetPasswordActivity.class);
                                    intent.putExtra("userId", userId);
                                    intent.putExtra("pWord", codeNumberString);
                                    startActivity(intent);
                                }
                            }
                        })
                        //.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        //    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        //    }
                        //})
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

    }


}
