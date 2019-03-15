package com.saiyi.oldmanwatch.entity;

/**
 * @Author liwenbo
 * @Date 18/10/23
 * @Describe
 */
public class RequestApplyBind {
    private String filiation;
    private String mac;
    private String relationName;
    private int uid;

//    filiation	关系	string
//    mac	设备编号	string
//    relationName	姓名	string
//    type	设备类型	string
//    uid	用户id	number
//
    private String  type;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFiliation() {
        return filiation;
    }

    public void setFiliation(String filiation) {
        this.filiation = filiation;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "RequestApplyBind{" +
                "filiation='" + filiation + '\'' +
                ", mac='" + mac + '\'' +
                ", relationName='" + relationName + '\'' +
                ", uid=" + uid +
                '}';
    }
}
