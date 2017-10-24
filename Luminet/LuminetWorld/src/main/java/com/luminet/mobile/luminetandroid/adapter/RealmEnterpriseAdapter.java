package com.luminet.mobile.luminetandroid.adapter;

import android.content.Context;

import com.luminet.mobile.luminetandroid.data.EnterpriseDTO;

import io.realm.RealmResults;

/**
 * Created by chris on 2017/10/22.
 */

public class RealmEnterpriseAdapter extends RealmModelAdapter<EnterpriseDTO> {

    public RealmEnterpriseAdapter(Context context, RealmResults<EnterpriseDTO> realmResults, boolean automaticUpdate) {

        super(context, realmResults, automaticUpdate);
    }
}
