package com.luminet.mobile.luminetandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.luminet.mobile.luminetandroid.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.luminet.mobile.luminetandroid.app.LuminetSplashScreen.MyPREFERENCES;

/**
 * Created by chris on 2017/10/18.
 */

public class UpdatePasswordActivity extends Activity{
    Toolbar toolbar;
    TextView screenName;
    EditText currentPasssword, newPassword, confirmPassword;
    TextView changePasswordNumber;
    Button updatePasswordButton;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        setContentView(R.layout.change_password_view);
        //toolbar =  findViewById(R.layout.luminet_toolbar);
        changePasswordNumber = findViewById(R.id.change_password_number_label);
        changePasswordNumber.setText(sharedpreferences.getString("useId", null));
        currentPasssword = findViewById(R.id.current_password_input);
        newPassword = findViewById(R.id.new_password_text_input);
        confirmPassword = findViewById(R.id.confirm_password_text_input);
        updatePasswordButton = findViewById(R.id.change_password_button);
        updatePasswordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });

    }

    private void updatePassword(){
        if(currentPasssword.getText().equals("") || newPassword.getText().equals("") || confirmPassword.getText().equals("")){
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


    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    private void uploadNewPassword(){

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
