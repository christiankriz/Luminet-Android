package com.luminet.mobile.luminetandroid.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.luminet.mobile.luminetandroid.R;
import com.luminet.mobile.luminetandroid.adapter.EnterpriseAdapter;
import com.luminet.mobile.luminetandroid.adapter.EnterpriseServiceAdapter;
import com.luminet.mobile.luminetandroid.adapter.RealmEnterpriseAdapter;
import com.luminet.mobile.luminetandroid.adapter.RealmEnterpriseServiceAdapter;
import com.luminet.mobile.luminetandroid.data.EnterpriseDTO;
import com.luminet.mobile.luminetandroid.data.EnterpriseServiceDTO;
import com.luminet.mobile.luminetandroid.realm.RealmController;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by chris on 2017/10/08.
 */

public class CompayProfileActivity extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    RealmResults<EnterpriseServiceDTO> enterpriseServiceDTOs;
    EnterpriseServiceAdapter enterpriseServiceAdapter;
    Toolbar toolbar;
    String enterpriseId;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_profile);
        //toolbar =  findViewById(R.id.toolbar);
        //toolbar.setTitle("Company Profile");
        recyclerView = (RecyclerView) findViewById(R.id.enterprise_services_recycler_view);
        String companyName = null, message = null;
        byte[] decodedString = null;
        TextView company = findViewById(R.id.company);
        company.setTypeface(null, Typeface.BOLD_ITALIC);
        TextView messageTxt = findViewById(R.id.company_short_description_text);
        ImageView image = findViewById(R.id.company_logo);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            enterpriseId = bundle.getString("enterpriseId");
        }
        realm = Realm.getDefaultInstance();
        //Execute query
        EnterpriseDTO enterpriseDTO = realm.where(EnterpriseDTO.class).equalTo("id", enterpriseId).findFirst();
        SpannableString content = new SpannableString(enterpriseDTO.getDisplayName());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        company.setText(content);
        messageTxt.setText(enterpriseDTO.getShortDescription());
        Bitmap decodedByte = getBitmap(enterpriseDTO.getEnterpriseLogo());
        image.setImageBitmap(decodedByte);
        setupRecycler(enterpriseDTO.getId());
    }

    private Bitmap getBitmap(String stringBitmap){
        Bitmap bitmap = null;
        String base64Image = null;
        if(stringBitmap.contains(",")) {
            base64Image = stringBitmap.split(",")[1];
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
        return bitmap;
    }

    private void setupRecycler(String enterpriseId) {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager since the cards are vertically scrollable
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RealmQuery<EnterpriseServiceDTO> query = realm.where(EnterpriseServiceDTO.class);

        //Add query conditions
        //query.equalTo("enterpriseID", enterpriseId);

        //Execute query
        enterpriseServiceDTOs = query.findAll();

        // create an empty adapter and add it to the recycler view
        enterpriseServiceAdapter = new EnterpriseServiceAdapter(this, enterpriseServiceDTOs);
        recyclerView.setAdapter(enterpriseServiceAdapter);
        //RealmEnterpriseServiceAdapter realmAdapter = new RealmEnterpriseServiceAdapter(this.getApplicationContext(), enterpriseServiceDTOs, true);
        //enterpriseServiceAdapter.setRealmAdapter(realmAdapter);
        //enterpriseServiceAdapter.notifyDataSetChanged();
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
