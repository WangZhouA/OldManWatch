package com.saiyi.oldmanwatch.entity;

import java.util.List;

/**
 * Created by 最帅的男人 on 2019/2/25.
 */
public class UserMangerMacBean {


    /**
     * success : true
     * code : 1000
     * message : 操作成功！
     * data : [{"did":null,"date":null,"mac":null,"dayStep":null,"steps":null,"state":null,"type":"1","startDate":null,"endDate":null,"totalSleep":null,"sleeps":null,"deepSleep":null,"lightSleep":null,"hearts":null,"setup":null,"address":null,"headUrl":null,"bloodDate":null,"heart":null,"heartDate":null,"pressure":null,"sleepDate":null,"step":null,"stepsDate":null,"uid":1,"users":null,"opinions":null,"devices":null,"messages":null,"deviceinfos":null,"heartPressure":null,"stepNumber":null,"log":null,"lat":null,"id":null,"phone":"13800138000","password":null,"sex":null,"age":null,"name":"流沙","token":null,"imgUrl":null,"code":null,"filiation":null,"height":null,"openid":null,"birthday":null}]
     */

    private boolean success;
    private int code;
    private String message;
    /**
     * did : null
     * date : null
     * mac : null
     * dayStep : null
     * steps : null
     * state : null
     * type : 1
     * startDate : null
     * endDate : null
     * totalSleep : null
     * sleeps : null
     * deepSleep : null
     * lightSleep : null
     * hearts : null
     * setup : null
     * address : null
     * headUrl : null
     * bloodDate : null
     * heart : null
     * heartDate : null
     * pressure : null
     * sleepDate : null
     * step : null
     * stepsDate : null
     * uid : 1
     * users : null
     * opinions : null
     * devices : null
     * messages : null
     * deviceinfos : null
     * heartPressure : null
     * stepNumber : null
     * log : null
     * lat : null
     * id : null
     * phone : 13800138000
     * password : null
     * sex : null
     * age : null
     * name : 流沙
     * token : null
     * imgUrl : null
     * code : null
     * filiation : null
     * height : null
     * openid : null
     * birthday : null
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
        private Object did;
        private Object date;
        private Object mac;
        private Object dayStep;
        private Object steps;
        private Object state;
        private String type;
        private Object startDate;
        private Object endDate;
        private Object totalSleep;
        private Object sleeps;
        private Object deepSleep;
        private Object lightSleep;
        private Object hearts;
        private Object setup;
        private Object address;
        private Object headUrl;
        private Object bloodDate;
        private Object heart;
        private Object heartDate;
        private Object pressure;
        private Object sleepDate;
        private Object step;
        private Object stepsDate;
        private int uid;
        private Object users;
        private Object opinions;
        private Object devices;
        private Object messages;
        private Object deviceinfos;
        private Object heartPressure;
        private Object stepNumber;
        private Object log;
        private Object lat;
        private Object id;
        private String phone;
        private Object password;
        private Object sex;
        private Object age;
        private String name;
        private Object token;
        private Object imgUrl;
        private Object code;
        private Object filiation;
        private Object height;
        private Object openid;
        private Object birthday;

        public Object getDid() {
            return did;
        }

        public void setDid(Object did) {
            this.did = did;
        }

        public Object getDate() {
            return date;
        }

        public void setDate(Object date) {
            this.date = date;
        }

        public Object getMac() {
            return mac;
        }

        public void setMac(Object mac) {
            this.mac = mac;
        }

        public Object getDayStep() {
            return dayStep;
        }

        public void setDayStep(Object dayStep) {
            this.dayStep = dayStep;
        }

        public Object getSteps() {
            return steps;
        }

        public void setSteps(Object steps) {
            this.steps = steps;
        }

        public Object getState() {
            return state;
        }

        public void setState(Object state) {
            this.state = state;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getStartDate() {
            return startDate;
        }

        public void setStartDate(Object startDate) {
            this.startDate = startDate;
        }

        public Object getEndDate() {
            return endDate;
        }

        public void setEndDate(Object endDate) {
            this.endDate = endDate;
        }

        public Object getTotalSleep() {
            return totalSleep;
        }

        public void setTotalSleep(Object totalSleep) {
            this.totalSleep = totalSleep;
        }

        public Object getSleeps() {
            return sleeps;
        }

        public void setSleeps(Object sleeps) {
            this.sleeps = sleeps;
        }

        public Object getDeepSleep() {
            return deepSleep;
        }

        public void setDeepSleep(Object deepSleep) {
            this.deepSleep = deepSleep;
        }

        public Object getLightSleep() {
            return lightSleep;
        }

        public void setLightSleep(Object lightSleep) {
            this.lightSleep = lightSleep;
        }

        public Object getHearts() {
            return hearts;
        }

        public void setHearts(Object hearts) {
            this.hearts = hearts;
        }

        public Object getSetup() {
            return setup;
        }

        public void setSetup(Object setup) {
            this.setup = setup;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(Object headUrl) {
            this.headUrl = headUrl;
        }

        public Object getBloodDate() {
            return bloodDate;
        }

        public void setBloodDate(Object bloodDate) {
            this.bloodDate = bloodDate;
        }

        public Object getHeart() {
            return heart;
        }

        public void setHeart(Object heart) {
            this.heart = heart;
        }

        public Object getHeartDate() {
            return heartDate;
        }

        public void setHeartDate(Object heartDate) {
            this.heartDate = heartDate;
        }

        public Object getPressure() {
            return pressure;
        }

        public void setPressure(Object pressure) {
            this.pressure = pressure;
        }

        public Object getSleepDate() {
            return sleepDate;
        }

        public void setSleepDate(Object sleepDate) {
            this.sleepDate = sleepDate;
        }

        public Object getStep() {
            return step;
        }

        public void setStep(Object step) {
            this.step = step;
        }

        public Object getStepsDate() {
            return stepsDate;
        }

        public void setStepsDate(Object stepsDate) {
            this.stepsDate = stepsDate;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public Object getUsers() {
            return users;
        }

        public void setUsers(Object users) {
            this.users = users;
        }

        public Object getOpinions() {
            return opinions;
        }

        public void setOpinions(Object opinions) {
            this.opinions = opinions;
        }

        public Object getDevices() {
            return devices;
        }

        public void setDevices(Object devices) {
            this.devices = devices;
        }

        public Object getMessages() {
            return messages;
        }

        public void setMessages(Object messages) {
            this.messages = messages;
        }

        public Object getDeviceinfos() {
            return deviceinfos;
        }

        public void setDeviceinfos(Object deviceinfos) {
            this.deviceinfos = deviceinfos;
        }

        public Object getHeartPressure() {
            return heartPressure;
        }

        public void setHeartPressure(Object heartPressure) {
            this.heartPressure = heartPressure;
        }

        public Object getStepNumber() {
            return stepNumber;
        }

        public void setStepNumber(Object stepNumber) {
            this.stepNumber = stepNumber;
        }

        public Object getLog() {
            return log;
        }

        public void setLog(Object log) {
            this.log = log;
        }

        public Object getLat() {
            return lat;
        }

        public void setLat(Object lat) {
            this.lat = lat;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }

        public Object getAge() {
            return age;
        }

        public void setAge(Object age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getToken() {
            return token;
        }

        public void setToken(Object token) {
            this.token = token;
        }

        public Object getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(Object imgUrl) {
            this.imgUrl = imgUrl;
        }

        public Object getCode() {
            return code;
        }

        public void setCode(Object code) {
            this.code = code;
        }

        public Object getFiliation() {
            return filiation;
        }

        public void setFiliation(Object filiation) {
            this.filiation = filiation;
        }

        public Object getHeight() {
            return height;
        }

        public void setHeight(Object height) {
            this.height = height;
        }

        public Object getOpenid() {
            return openid;
        }

        public void setOpenid(Object openid) {
            this.openid = openid;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }
    }
}
