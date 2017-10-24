package com.luminet.mobile.luminetandroid.realm;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;

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