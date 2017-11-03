package com.luminet.mobile.luminetandroid.adapter;

import android.content.Context;

import com.luminet.mobile.luminetandroid.data.EnterpriseDTO;
import com.luminet.mobile.luminetandroid.data.EnterpriseServiceDTO;

import io.realm.RealmResults;

/**
 * Created by chris on 2017/11/03.
 */

public class RealmEnterpriseServiceAdapter extends RealmModelAdapter<EnterpriseServiceDTO> {

    public RealmEnterpriseServiceAdapter(Context context, RealmResults<EnterpriseServiceDTO> realmResults, boolean automaticUpdate) {

        super(context, realmResults, automaticUpdate);
    }
}
