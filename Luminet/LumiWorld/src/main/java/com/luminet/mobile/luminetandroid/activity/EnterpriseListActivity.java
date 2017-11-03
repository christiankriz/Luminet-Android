package com.luminet.mobile.luminetandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import com.luminet.mobile.luminetandroid.R;
import com.luminet.mobile.luminetandroid.adapter.EnterpriseAdapter;
import com.luminet.mobile.luminetandroid.adapter.RealmEnterpriseAdapter;
import com.luminet.mobile.luminetandroid.data.EnterpriseDTO;
import com.luminet.mobile.luminetandroid.realm.RealmController;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by chris on 2017/10/20.
 */

public class EnterpriseListActivity extends Activity {
    private RecyclerView recyclerView;
    //private LayoutInflater inflater;
    private RecyclerView.LayoutManager layoutManager;
    RealmResults<EnterpriseDTO> enterprises;
    Realm realm;
    EnterpriseAdapter enterpriseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enterprise_activity);
        //get realm instance
        this.realm = RealmController.with(this).getRealm();
        recyclerView = (RecyclerView) findViewById(R.id.my_enterprise_recycler_view);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        String parentId = null;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            parentId = bundle.getString("parentId");
        }
        // refresh the realm instance
        RealmController.with(this).refresh();
        setupRecycler(parentId);
    }

    private void setupRecycler(String parentId) {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager since the cards are vertically scrollable
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RealmQuery<EnterpriseDTO> query = realm.where(EnterpriseDTO.class);

        //Add query conditions
        query.equalTo("parentid", parentId);

        //Execute query
        enterprises = query.findAll();

        // create an empty adapter and add it to the recycler view
        enterpriseAdapter = new EnterpriseAdapter(this, enterprises);
        recyclerView.setAdapter(enterpriseAdapter);
        RealmEnterpriseAdapter realnAdapter = new RealmEnterpriseAdapter(this.getApplicationContext(), enterprises, true);
        enterpriseAdapter.setRealmAdapter(realnAdapter);
        enterpriseAdapter.notifyDataSetChanged();
        //recyclerView.bringChildToFront(layoutManager);
    }

}
