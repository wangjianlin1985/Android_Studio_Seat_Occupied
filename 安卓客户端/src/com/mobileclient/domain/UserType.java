package com.mobileclient.domain;

import java.io.Serializable;

public class UserType implements Serializable {
    /*�û�����id*/
    private int userTypeId;
    public int getUserTypeId() {
        return userTypeId;
    }
    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    /*�û���������*/
    private String userTypeName;
    public String getUserTypeName() {
        return userTypeName;
    }
    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

}