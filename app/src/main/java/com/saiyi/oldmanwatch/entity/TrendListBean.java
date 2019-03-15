package com.saiyi.oldmanwatch.entity;

import java.util.List;

/**
 * Created by 最帅的男人 on 2019/2/26.
 */
public class TrendListBean {


    /**
     * success : true
     * code : 1000
     * message : 操作成功！
     * data : [{"currPage":null,"size":null,"start":null,"uid":null,"date":"2019-02-25","parameters":[{"id":null,"uid":null,"weight":48,"score":86,"size":2,"bodyFatRate":0,"muscleRate":0,"waterContent":0,"boneSalt":0,"fatLevel":0,"basalMetabolism":0,"muscle":45,"bmi":24,"createDate":"16:42:30","startDate":null,"type":null,"week":"Monday","start":null,"end":null,"month":null}]}]
     */

    private boolean success;
    private int code;
    private String message;
    /**
     * currPage : null
     * size : null
     * start : null
     * uid : null
     * date : 2019-02-25
     * parameters : [{"id":null,"uid":null,"weight":48,"score":86,"size":2,"bodyFatRate":0,"muscleRate":0,"waterContent":0,"boneSalt":0,"fatLevel":0,"basalMetabolism":0,"muscle":45,"bmi":24,"createDate":"16:42:30","startDate":null,"type":null,"week":"Monday","start":null,"end":null,"month":null}]
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
        private Object currPage;
        private Object size;
        private Object start;
        private int  uid;
        private String date;
        /**
         * id : null
         * uid : null
         * weight : 48
         * score : 86
         * size : 2
         * bodyFatRate : 0
         * muscleRate : 0
         * waterContent : 0
         * boneSalt : 0
         * fatLevel : 0
         * basalMetabolism : 0
         * muscle : 45
         * bmi : 24
         * createDate : 16:42:30
         * startDate : null
         * type : null
         * week : Monday
         * start : null
         * end : null
         * month : null
         */

        private List<ParametersBean> parameters;

        public Object getCurrPage() {
            return currPage;
        }

        public void setCurrPage(Object currPage) {
            this.currPage = currPage;
        }

        public Object getSize() {
            return size;
        }

        public void setSize(Object size) {
            this.size = size;
        }

        public Object getStart() {
            return start;
        }

        public void setStart(Object start) {
            this.start = start;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<ParametersBean> getParameters() {
            return parameters;
        }

        public void setParameters(List<ParametersBean> parameters) {
            this.parameters = parameters;
        }

        public static class ParametersBean {
            private String id;
            private String uid;
            private String weight;
            private String score;
            private String size;
            private String bodyFatRate;
            private String muscleRate;
            private String waterContent;
            private String boneSalt;
            private String fatLevel;
            private String basalMetabolism;
            private String muscle;
            private String bmi;
            private String createDate;
            private String startDate;
            private String type;
            private String week;
            private String start;
            private String end;
            private String month;

            public Object getId() {
                return id;
            }

            public void setId(String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       id) {
                this.id = id;
            }

            public Object getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public String getBodyFatRate() {
                return bodyFatRate;
            }

            public void setBodyFatRate(String bodyFatRate) {
                this.bodyFatRate = bodyFatRate;
            }

            public String getMuscleRate() {
                return muscleRate;
            }

            public void setMuscleRate(String muscleRate) {
                this.muscleRate = muscleRate;
            }

            public String getWaterContent() {
                return waterContent;
            }

            public void setWaterContent(String waterContent) {
                this.waterContent = waterContent;
            }

            public String getBoneSalt() {
                return boneSalt;
            }

            public void setBoneSalt(String boneSalt) {
                this.boneSalt = boneSalt;
            }

            public String getFatLevel() {
                return fatLevel;
            }

            public void setFatLevel(String fatLevel) {
                this.fatLevel = fatLevel;
            }

            public String getBasalMetabolism() {
                return basalMetabolism;
            }

            public void setBasalMetabolism(String basalMetabolism) {
                this.basalMetabolism = basalMetabolism;
            }

            public String getMuscle() {
                return muscle;
            }

            public void setMuscle(String muscle) {
                this.muscle = muscle;
            }

            public String getBmi() {
                return bmi;
            }

            public void setBmi(String bmi) {
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

            public Object getType() {
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

            public Object getStart() {
                return start;
            }

            public void setStart(String start) {
                this.start = start;
            }

            public Object getEnd() {
                return end;
            }

            public void setEnd(String end) {
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
}
