package com.luminet.mobile.luminetandroid.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
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

/**
 * Created by chris on 2017/10/18.
 */

public class UpdatePasswordActivity extends Activity{
    Toolbar toolbar;
    TextView screenName;
    EditText currentPasssword, newPassword, confirmPassword;
    TextView changePasswordNumber;
    Button updatePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_view);
        //toolbar =  findViewById(R.layout.luminet_toolbar);
        changePasswordNumber = findViewById(R.id.change_password_number_label);
        //screenName = toolbar.findViewById(R.id.toolbar_subtitle);
        //screenName.setText("Profile");
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
