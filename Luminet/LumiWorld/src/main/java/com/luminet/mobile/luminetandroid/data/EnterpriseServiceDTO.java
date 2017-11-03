package com.luminet.mobile.luminetandroid.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by chris on 2017/11/01.
 */

public class EnterpriseServiceDTO extends RealmObject {
    @PrimaryKey
    private String serviceID;
    private String status;
    private String enterpriseID;
    private String id;

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

    public String getEnterpriseID() {
        return enterpriseID;
    }

    public void setEnterpriseID(String enterpriseID) {
        this.enterpriseID = enterpriseID;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }
}
