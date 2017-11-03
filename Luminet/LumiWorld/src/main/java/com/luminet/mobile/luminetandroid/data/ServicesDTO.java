package com.luminet.mobile.luminetandroid.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by chris on 2017/11/01.
 */

public class ServicesDTO extends RealmObject {
    @PrimaryKey
    private String id;
    private String status;
    private String displayName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
