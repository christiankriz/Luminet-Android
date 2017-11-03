package com.luminet.mobile.luminetandroid.realm;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.luminet.mobile.luminetandroid.api.DataService;
import com.luminet.mobile.luminetandroid.data.EnterpriseDTO;
import com.luminet.mobile.luminetandroid.data.EnterpriseServiceDTO;
import com.luminet.mobile.luminetandroid.data.NodeRelationshipDTO;
import com.luminet.mobile.luminetandroid.data.ServicesDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

/**
 * Created by chris on 2017/11/01.
 */

public class DataReceiver {

    public  DataReceiver(){

    }

    public void addEnterprise(String jsonString){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        String id;
        JSONArray array = null;
        try {
            array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject row = array.getJSONObject(i);
                JsonParser parser = new JsonParser();
                JsonElement mJson =  parser.parse(String.valueOf(row));
                Gson gson = new Gson();
                EnterpriseDTO object = gson.fromJson(mJson, EnterpriseDTO.class);
                id = object.getId();
                if (realm.where(EnterpriseDTO.class).equalTo("id", id).count() == 0) {
                    // there are no object with this `Id`
                    realm.copyToRealmOrUpdate(object);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        realm.commitTransaction();
    }

    public void addServices(String jsonString){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        String id;
        JSONArray array = null;
        try {
            array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject row = array.getJSONObject(i);
                JsonParser parser = new JsonParser();
                JsonElement mJson =  parser.parse(String.valueOf(row));
                Gson gson = new Gson();
                ServicesDTO object = gson.fromJson(mJson, ServicesDTO.class);
                id = object.getId();
                if (realm.where(ServicesDTO.class).equalTo("id", id).count() == 0) {
                    // there are no object with this `Id`
                    realm.copyToRealmOrUpdate(object);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        realm.commitTransaction();
    }

    public void addNodeByRelationship(String jsonString){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        String id;
        JSONArray array = null;
        try {
            array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject row = array.getJSONObject(i);
                JsonParser parser = new JsonParser();
                JsonElement mJson =  parser.parse(String.valueOf(row));
                Gson gson = new Gson();
                NodeRelationshipDTO object = gson.fromJson(mJson, NodeRelationshipDTO.class);
                id = object.getID();
                if(id != null){
                    if (realm.where(ServicesDTO.class).equalTo("id", id).count() == 0 && id != null) {
                        // there are no object with this `Id`
                        realm.copyToRealmOrUpdate(object);
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        realm.commitTransaction();
    }

    public void addAllEnterpriseService(String jsonString){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        String id;
        JSONArray array = null;
        try {
            array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject row = array.getJSONObject(i);
                JsonParser parser = new JsonParser();
                JsonElement mJson =  parser.parse(String.valueOf(row));
                Gson gson = new Gson();
                EnterpriseServiceDTO object = gson.fromJson(mJson, EnterpriseServiceDTO.class);
                id = object.getId();
                if (realm.where(ServicesDTO.class).equalTo("id", id).count() == 0) {
                    // there are no object with this `Id`
                    realm.copyToRealmOrUpdate(object);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        realm.commitTransaction();
    }
}
