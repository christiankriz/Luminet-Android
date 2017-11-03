package com.luminet.mobile.luminetandroid.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by chris on 2017/11/01.
 */

public class NodeRelationshipDTO extends RealmObject {
    @PrimaryKey
    private String ID;
    private String status;
    private String date;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
