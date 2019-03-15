package com.saiyi.oldmanwatch.entity;

import java.util.List;

/**
 * Created by 最帅的男人 on 2019/2/22.
 */
public class WeightTrends {


    /**
     * success : true
     * code : 1000
     * message : 操作成功！
     * data : [{"id":null,"uid":null,"weight":58,"score":null,"size":null,"bodyFatRate":0,"muscleRate":0,"waterContent":0,"boneSalt":0,"fatLevel":0,"basalMetabolism":0,"muscle":0,"bmi":0,"createDate":"2019-01-01","startDate":null,"type":null,"week":null,"start":null,"end":null,"month":null},{"id":null,"uid":null,"weight":62,"score":null,"size":null,"bodyFatRate":0,"muscleRate":0,"waterContent":0,"boneSalt":0,"fatLevel":0,"basalMetabolism":0,"muscle":0,"bmi":0,"createDate":"2019-01-05","startDate":null,"type":null,"week":null,"start":null,"end":null,"month":null},{"id":null,"uid":null,"weight":66,"score":null,"size":null,"bodyFatRate":0,"muscleRate":0,"waterContent":0,"boneSalt":0,"fatLevel":0,"basalMetabolism":0,"muscle":0,"bmi":0,"createDate":"2019-01-06","startDate":null,"type":null,"week":null,"start":null,"end":null,"month":null},{"id":null,"uid":null,"weight":60,"score":null,"size":null,"bodyFatRate":0,"muscleRate":0,"waterContent":0,"boneSalt":0,"fatLevel":0,"basalMetabolism":0,"muscle":0,"bmi":0,"createDate":"2019-02-19","startDate":null,"type":null,"week":null,"start":null,"end":null,"month":null},{"id":null,"uid":null,"weight":49,"score":null,"size":null,"bodyFatRate":0,"muscleRate":0,"waterContent":0,"boneSalt":0,"fatLevel":0,"basalMetabolism":0,"muscle":0,"bmi":0,"createDate":"2019-02-23","startDate":null,"type":null,"week":null,"start":null,"end":null,"month":null},{"id":null,"uid":null,"weight":48.5,"score":null,"size":null,"bodyFatRate":0,"muscleRate":0,"waterContent":0,"boneSalt":0,"fatLevel":0,"basalMetabolism":0,"muscle":0,"bmi":0,"createDate":"2019-02-25","startDate":null,"type":null,"week":null,"start":null,"end":null,"month":null}]
     */

    private boolean success;
    private int code;
    private String message;
    /**
     * id : null
     * uid : null
     * weight : 58
     * score : null
     * size : null
     * bodyFatRate : 0
     * muscleRate : 0
     * waterContent : 0
     * boneSalt : 0
     * fatLevel : 0
     * basalMetabolism : 0
     * muscle : 0
     * bmi : 0
     * createDate : 2019-01-01
     * startDate : null
     * type : null
     * week : null
     * start : null
     * end : null
     * month : null
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
        private Object id;
        private Object uid;
        private double weight;
        private Object score;
        private Object size;
        private int bodyFatRate;
        private int muscleRate;
        private int waterContent;
        private int boneSalt;
        private int fatLevel;
        private int basalMetabolism;
        private int muscle;
        private int bmi;
        private String createDate;
        private String startDate;
        private String type;
        private String week;
        private long start;
        private long end;
        private String month;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getUid() {
            return uid;
        }

        public void setUid(Object uid) {
            this.uid = uid;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public Object getScore() {
            return score;
        }

        public void setScore(Object score) {
            this.score = score;
        }

        public Object getSize() {
            return size;
        }

        public void setSize(Object size) {
            this.size = size;
        }

        public int getBodyFatRate() {
            return bodyFatRate;
        }

        public void setBodyFatRate(int bodyFatRate) {
            this.bodyFatRate = bodyFatRate;
        }

        public int getMuscleRate() {
            return muscleRate;
        }

        public void setMuscleRate(int muscleRate) {
            this.muscleRate = muscleRate;
        }

        public int getWaterContent() {
            return waterContent;
        }

        public void setWaterContent(int waterContent) {
            this.waterContent = waterContent;
        }

        public int getBoneSalt() {
            return boneSalt;
        }

        public void setBoneSalt(int boneSalt) {
            this.boneSalt = boneSalt;
        }

        public int getFatLevel() {
            return fatLevel;
        }

        public void setFatLevel(int fatLevel) {
            this.fatLevel = fatLevel;
        }

        public int getBasalMetabolism() {
            return basalMetabolism;
        }

        public void setBasalMetabolism(int basalMetabolism) {
            this.basalMetabolism = basalMetabolism;
        }

        public int getMuscle() {
            return muscle;
        }

        public void setMuscle(int muscle) {
            this.muscle = muscle;
        }

        public int getBmi() {
            return bmi;
        }

        public void setBmi(int bmi) {
            this.bmi = bmi;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public Object getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public long getStart() {
            return start;
        }

        public void setStart(long start) {
            this.start = start;
        }

        public long getEnd() {
            return end;
        }

        public void setEnd(long end) {
            this.end = end;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }
    }
}
