package com.luminet.mobile.luminetandroid.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.luminet.mobile.luminetandroid.R;

/**
 * Created by chris on 2017/10/10.
 */

public class LoginMain extends Activity implements View.OnClickListener{

    Button loginButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_login);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.register_button:
                Intent i = new Intent(getBaseContext(), RegisterUser.class);
                startActivity(i);
                break;

            case R.id.login_button:
                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
                break;

            default:
                break;
        }

    }
}
