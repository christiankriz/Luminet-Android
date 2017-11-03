package com.luminet.mobile.luminetandroid.realm;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.luminet.mobile.luminetandroid.data.EnterpriseDTO;
import com.luminet.mobile.luminetandroid.data.NodeRelationshipDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static com.luminet.mobile.luminetandroid.app.LuminetSplashScreen.MyPREFERENCES;

/**
 * Created by chris on 2017/10/22.
 */

public class RealmController{
    private static RealmController instance;
        private final Realm realm;

        public RealmController(Application application) {
            realm = Realm.getDefaultInstance();
        }

        public static RealmController with(Fragment fragment) {

            if (instance == null) {
                instance = new RealmController(fragment.getActivity().getApplication());
            }
            return instance;
        }

        public static RealmController with(Activity activity) {

            if (instance == null) {
                instance = new RealmController(activity.getApplication());
            }
            return instance;
        }

        public static RealmController with(Application application) {

            if (instance == null) {
                instance = new RealmController(application);
            }
            return instance;
        }

        public static RealmController getInstance() {

            return instance;
        }

        public Realm getRealm() {

            return realm;
        }

        //Refresh the realm istance
        public void refresh() {

            //realm.refresh();
        }

        //clear all objects from Book.class
        public void clearAll() {

            realm.beginTransaction();
            //realm.clear(Book.class);
            realm.commitTransaction();
        }





}