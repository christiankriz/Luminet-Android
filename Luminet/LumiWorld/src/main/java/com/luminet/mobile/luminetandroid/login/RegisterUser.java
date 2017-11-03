package com.luminet.mobile.luminetandroid.login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.luminet.mobile.luminetandroid.R;
import com.luminet.mobile.luminetandroid.api.MuleAPI;
import com.luminet.mobile.luminetandroid.data.RegisterationResponseDTO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static android.content.ContentValues.TAG;
import android.app.ProgressDialog;


/**
 * Created by chris on 2017/10/11.
 */

public class RegisterUser extends Activity {
    private final OkHttpClient client = new OkHttpClient();
    Button registerNextButton;
    EditText mobileNumber;
    private Toolbar toolbar;
    private Snackbar snackbar;
    String mobileNum;

    public static final String MyPREFERENCES = "LumiPrefs" ;
    public static final String userId = "useId";
    public static final String pWord = "pWord";
    public static final String Email = "emailKey";
    ProgressDialog dialog;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.user_registration);
                toolbar = (Toolbar) findViewById(R.id.toolbar);
                mobileNumber = findViewById(R.id.register_number_text_input);
                registerNextButton = findViewById(R.id.register_next_button);
                registerNextButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        registerUser(v);
                    }
                });
        }

        private void registerUser(View view){
            Button button = (Button) view;
            mobileNum = validateNumber();
            //Date currentTime = Calendar.getInstance().getTime();
            String currentTime = (String) android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new Date());
            String paramData = "{\"properties\":{\"id\":\"" + mobileNum + "\",\"cell\":\"" + mobileNum + "\",\"status\":1,\"createDate\":\"" + currentTime + "\",\"appVersion\":\"0.5.28.125\"},\"label\":\"Consumer\",\"key\":\"cell\"}";
            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("params", paramData);
            RequestBody formBody = formBuilder.build();
            Request request = new Request.Builder()
                    .url(MuleAPI.registerUserUrl)
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
                           if(respondData.equals("\"0\"")){
                               String message = "The number you are trying to register is already registered";
                               String title = "Register User";
                               displayMessage(message, title, "0");
                               //showSnackbar(title, message);
                           }else{
                               String message = "An SMS with your verification code will be sent to:" + mobileNum;
                               String title = "Mobile Number Confirmation";
                               displayMessage(message, title, respondData);
                           }
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
                    displayMessage(message, title, "error");
                    Log.v(TAG, "Registration Exception : ", e);
                }
            });

        }

        private String validateNumber(){
            String num = mobileNumber.getText().toString();
            if(num.length() != 10){
                String message = "Please enter 10 digit number";
                String title = "Register";
                displayMessage(message, title, "error");
            }
            num = num.substring(1);
            num = "27" + num;
            return  num;
        }

    public void showSnackbar(String title, String action) {
        snackbar = Snackbar.make(toolbar, title, Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(Color.green(100));
        snackbar.setAction(action, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();

    }


            private void displayMessage(final String message, final String title, final String response){

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(RegisterUser.this, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(RegisterUser.this);
                        }
                        builder.setTitle(title)
                                .setMessage(message)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(title.equals("Mobile Number Confirmation")){
                                            RegisterationResponseDTO registerationResponseDTO = new RegisterationResponseDTO();
                                            JSONObject json = null;
                                            try {
                                                json = new JSONObject(response);
                                                registerationResponseDTO.setId(json.getString("id"));
                                                registerationResponseDTO.setStatus(json.getString("status"));
                                                registerationResponseDTO.setAppVersion(json.getString("appVersion"));
                                                registerationResponseDTO.setCell(json.getString("cell"));
                                                registerationResponseDTO.setCreateDate(json.getString("createDate"));
                                                requestSmsValidation(registerationResponseDTO);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


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

            private  void requestSmsValidation(RegisterationResponseDTO registerationResponseDTO){
                final String userId = registerationResponseDTO.getCell();
                String messageValues = "{\"cellPhoneNumber\":\"" + userId + "\"}";
                FormBody.Builder formBuilder = new FormBody.Builder()
                        .add("params", messageValues);
                RequestBody formBody = formBuilder.build();
                Request request = new Request.Builder()
                        .url(MuleAPI.requestSmsUrl)
                        .post(formBody)
                        .build();
                Call call = client.newCall(request);
                MuleAPI muleAPI = new MuleAPI();
                // Start download
                dialog = new ProgressDialog(RegisterUser.this);
                //set message of the dialog
                dialog.setMessage("Sending SMS request...");
                //show dialog
                dialog.show();
                muleAPI.getData(call, new MuleAPI.DataListener(){

                    @Override
                    public void onDataSucceeded(Call call, Response response){
                        String respondData = null;
                        try {
                            respondData = response.body().string();
                            if (response.isSuccessful()) {
                                if(respondData.equals("\"1\"")){
                                    if(dialog != null && dialog.isShowing()){
                                        dialog.dismiss();
                                    }
                                    Intent intent = new Intent(RegisterUser.this, UserVerification.class);
                                    intent.putExtra("userId", userId);
                                    startActivity(intent);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onDataFailed(Call call, IOException e){
                        if(dialog != null && dialog.isShowing()){
                            dialog.dismiss();
                        }
                        String message = "We could not connect to Luminet World please check you internet connectivity and try again";
                        String title = "Error Registration";
                        displayMessage(message, title, "error");
                        Log.v(TAG, "SMS Exception : ", e);
                    }

                });
            }
}



