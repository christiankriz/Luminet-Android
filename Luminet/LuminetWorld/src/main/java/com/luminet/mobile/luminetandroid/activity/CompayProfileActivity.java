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
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.luminet.mobile.luminetandroid.R;

/**
 * Created by chris on 2017/10/08.
 */

public class CompayProfileActivity extends Activity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_profile);
        //toolbar =  findViewById(R.id.toolbar);
        //toolbar.setTitle("Company Profile");
        String companyName = null, message = null;
        byte[] decodedString = null;
        TextView company = findViewById(R.id.company);
        company.setTypeface(null, Typeface.BOLD_ITALIC);
        TextView messageTxt = findViewById(R.id.message);
        ImageView image = findViewById(R.id.company_logo);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            companyName = bundle.getString("companyName");
            message = bundle.getString("messageBody");
            decodedString = bundle.getByteArray("image");
        }
        SpannableString content = new SpannableString(companyName);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        company.setText(content);
        //messageTxt.setText(message);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        image.setImageBitmap(bitmap);
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
