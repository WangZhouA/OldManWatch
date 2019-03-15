package com.saiyi.oldmanwatch.entity;

import java.util.List;

/**
 * Created by 最帅的男人 on 2019/3/1.
 */
public class OnlyDeviceBean {


    /**
     * success : true
     * code : 1000
     * message : 操作成功！
     * data : [{"id":null,"uid":null,"relationName":null,"filiation":null,"shutdownState":null,"remindState":null,"sosState":null,"electricState":null,"electric":null,"scenePattern":null,"workPattern":null,"type":"3","headUrl":null,"n":null,"e":null,"date":null,"mac":"123456789101234","did":null,"name":"体脂称","phone":null,"state":null,"phones":null}]
     */

    private boolean success;
    private int code;
    private String message;
    /**
     * id : null
     * uid : null
     * relationName : null
     * filiation : null
     * shutdownState : null
     * remindState : null
     * sosState : null
     * electricState : null
     * electric : null
     * scenePattern : null
     * workPattern : null
     * type : 3
     * headUrl : null
     * n : null
     * e : null
     * date : null
     * mac : 123456789101234
     * did : null
     * name : 体脂称
     * phone : null
     * state : null
     * phones : null
     */

    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private int uid;
        private String relationName;
        private String filiation;
        private String shutdownState;
        private String remindState;
        private String sosState;
        private String electricState;
        private String electric;
        private String scenePattern;
        private String workPattern;
        private String type;
        private String headUrl;
        private String n;
        private String e;
        private String date;
        private String mac;
        private int did;
        private String name;
        private String phone;
        private String state;
        private String phones;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getRelationName() {
            return relationName;
        }

        public void setRelationName(String relationName) {
            this.relationName = relationName;
        }

        public String getFiliation() {
            return filiation;
        }

        public void setFiliation(String filiation) {
            this.filiation = filiation;
        }

        public String getShutdownState() {
            return shutdownState;
        }

        public void setShutdownState(String shutdownState) {
            this.shutdownState = shutdownState;
        }

        public String getRemindState() {
            return remindState;
        }

        public void setRemindState(String remindState) {
            this.remindState = remindState;
        }

        public String getSosState() {
            return sosState;
        }

        public void setSosState(String sosState) {
            this.sosState = sosState;
        }

        public String getElectricState() {
            return electricState;
        }

        public void setElectricState(String electricState) {
            this.electricState = electricState;
        }

        public String getElectric() {
            return electric;
        }

        public void setElectric(String electric) {
            this.electric = electric;
        }

        public String getScenePattern() {
            return scenePattern;
        }

        public void setScenePattern(String scenePattern) {
            this.scenePattern = scenePattern;
        }

        public String getWorkPattern() {
            return workPattern;
        }

        public void setWorkPattern(String workPattern) {
            this.workPattern = workPattern;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getN() {
            return n;
        }

        public void setN(String n) {
            this.n = n;
        }

        public String getE() {
            return e;
        }

        public void setE(String e) {
            this.e = e;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public int getDid() {
            return did;
        }

        public void setDid(int did) {
            this.did = did;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPhones() {
            return phones;
        }

        public void setPhones(String phones) {
            this.phones = phones;
        }
    }
}
