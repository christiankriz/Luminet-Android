package com.luminet.mobile.luminetandroid.realm;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.luminet.mobile.luminetandroid.data.NodeRelationshipDTO;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static com.luminet.mobile.luminetandroid.app.LuminetSplashScreen.MyPREFERENCES;

/**
 * Created by chris on 2017/11/01.
 */

public class RealmHelper {
    SharedPreferences sharedpreferences;
    Realm realm;
    Context context;

    public RealmHelper(Context context){
       this.context = context;
        realm = Realm.getDefaultInstance();
    }

    public boolean relashionshipExist(String enterpriseId){
        boolean exist = false;
        try {
            sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            String userId = sharedpreferences.getString("useId", null);
            enterpriseId = userId + "_" + enterpriseId;
            RealmQuery<NodeRelationshipDTO> query = realm.where(NodeRelationshipDTO.class);

            //Add query conditions
            //query.equalTo("ID", enterpriseId);

            //Execute query
            RealmResults<NodeRelationshipDTO> nodeRelationship = query.findAll();
            for(NodeRelationshipDTO node : nodeRelationship){
                if(node.getID().equals(enterpriseId)){
                    return exist = true;
                }

            }

        }catch (Exception e){
            Log.e("REALM error", e.toString());
        }
        return exist;
    }
}
