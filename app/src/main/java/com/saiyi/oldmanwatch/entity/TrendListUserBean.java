package com.saiyi.oldmanwatch.entity;

import java.util.List;

/**
 * Created by 最帅的男人 on 2019/2/26.
 */
public class TrendListUserBean {


    /**
     * success : true
     * code : 1000
     * message : 操作成功！
     * data : [{"currPage":null,"size":null,"start":null,"uid":null,"date":"2019-02-24","parameters":[{"id":null,"uid":null,"weight":48,"score":86,"size":2,"bodyFatRate":0,"muscleRate":0,"waterContent":0,"boneSalt":0,"fatLevel":0,"basalMetabolism":0,"muscle":45,"bmi":24,"createDate":"16:42:30","startDate":null,"type":null,"week":"Sunday","start":null,"end":null,"month":null},{"id":null,"uid":null,"weight":49,"score":86,"size":2,"bodyFatRate":0,"muscleRate":0,"waterContent":0,"boneSalt":0,"fatLevel":0,"basalMetabolism":0,"muscle":45,"bmi":24,"createDate":"08:21:44","startDate":null,"type":null,"week":"Sunday","start":null,"end":null,"month":null}]},{"currPage":null,"size":null,"start":null,"uid":null,"date":"2019-02-23","parameters":[{"id":null,"uid":null,"weight":49,"score":86,"size":2,"bodyFatRate":0,"muscleRate":0,"waterContent":0,"boneSalt":0,"fatLevel":0,"basalMetabolism":0,"muscle":45,"bmi":24,"createDate":"15:22:13","startDate":null,"type":null,"week":"Saturday","start":null,"end":null,"month":null}]},{"currPage":null,"size":null,"start":null,"uid":null,"date":"2019-02-19","parameters":[{"id":null,"uid":null,"weight":53,"score":86,"size":2,"bodyFatRate":0,"muscleRate":0,"waterContent":0,"boneSalt":0,"fatLevel":0,"basalMetabolism":0,"muscle":45,"bmi":24,"createDate":"18:42:13","startDate":null,"type":null,"week":"Tuesday","start":null,"end":null,"month":null},{"id":null,"uid":null,"weight":67,"score":86,"size":2,"bodyFatRate":0,"muscleRate":0,"waterContent":0,"boneSalt":0,"fatLevel":0,"basalMetabolism":0,"muscle":45,"bmi":24,"createDate":"11:12:13","startDate":null,"type":null,"week":"Tuesday","start":null,"end":null,"month":null}]},{"currPage":null,"size":null,"start":null,"uid":null,"date":"2019-01-06","parameters":[{"id":null,"uid":null,"weight":66,"score":86,"size":2,"bodyFatRate":0,"muscleRate":0,"waterContent":0,"boneSalt":0,"fatLevel":0,"basalMetabolism":0,"muscle":45,"bmi":24,"createDate":"15:22:13","startDate":null,"type":null,"week":"Sunday","start":null,"end":null,"month":null}]},{"currPage":null,"size":null,"start":null,"uid":null,"date":"2019-01-05","parameters":[{"id":null,"uid":null,"weight":62,"score":86,"size":2,"bodyFatRate":0,"muscleRate":0,"waterContent":0,"boneSalt":0,"fatLevel":0,"basalMetabolism":0,"muscle":45,"bmi":24,"createDate":"15:22:13","startDate":null,"type":null,"week":"Saturday","start":null,"end":null,"month":null}]},{"currPage":null,"size":null,"start":null,"uid":null,"date":"2019-01-01","parameters":[{"id":null,"uid":null,"weight":58,"score":86,"size":2,"bodyFatRate":0,"muscleRate":0,"waterContent":0,"boneSalt":0,"fatLevel":0,"basalMetabolism":0,"muscle":45,"bmi":24,"createDate":"15:22:13","startDate":null,"type":null,"week":"Tuesday","start":null,"end":null,"month":null}]}]
     */

    private boolean success;
    private int code;
    private String message;
    /**
     * currPage : null
     * size : null
     * start : null
     * uid : null
     * date : 2019-02-24
     * parameters : [{"id":null,"uid":null,"weight":48,"score":86,"size":2,"bodyFatRate":0,"muscleRate":0,"waterContent":0,"boneSalt":0,"fatLevel":0,"basalMetabolism":0,"muscle":45,"bmi":24,"createDate":"16:42:30","startDate":null,"type":null,"week":"Sunday","start":null,"end":null,"month":null},{"id":null,"uid":null,"weight":49,"score":86,"size":2,"bodyFatRate":0,"muscleRate":0,"waterContent":0,"boneSalt":0,"fatLevel":0,"basalMetabolism":0,"muscle":45,"bmi":24,"createDate":"08:21:44","startDate":null,"type":null,"week":"Sunday","start":null,"end":null,"month":null}]
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
        private int currPage;
        private int size;
        private long start;
        private int uid;
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
         * week : Sunday
         * start : null
         * end : null
         * month : null
         */

        private List<ParametersBean> parameters;

        public Object getCurrPage() {
            return currPage;
        }

        public void setCurrPage(int currPage) {
            this.currPage = currPage;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public long getStart() {
            return start;
        }

        public void setStart(long start) {
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
            private int id;
            private int uid;
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
            private long start;
            private long end;
            private String month;
            private String protein;

            private int bodyFatRateReference;
            private int muscleRateReference;
            private int basalMetabolismReference;
            private int bmiReference;
            private int boneSaltReference;
            private int fatLevelReference;
            private int muscleReference;
            private int proteinReference;
            private int waterContentReference;
            private int BoneSaltReference;


            public int getBodyFatRateReference() {
                return bodyFatRateReference;
            }

            public void setBodyFatRateReference(int bodyFatRateReference) {
                this.bodyFatRateReference = bodyFatRateReference;
            }

            public int getMuscleRateReference() {
                return muscleRateReference;
            }

            public void setMuscleRateReference(int muscleRateReference) {
                this.muscleRateReference = muscleRateReference;
            }

            public int getBasalMetabolismReference() {
                return basalMetabolismReference;
            }

            public void setBasalMetabolismReference(int basalMetabolismReference) {
                this.basalMetabolismReference = basalMetabolismReference;
            }

            public int getBmiReference() {
                return bmiReference;
            }

            public void setBmiReference(int bmiReference) {
                this.bmiReference = bmiReference;
            }

            public int getBoneSaltReference() {
                return boneSaltReference;
            }

            public void setBoneSaltReference(int boneSaltReference) {
                this.boneSaltReference = boneSaltReference;
            }

            public int getFatLevelReference() {
                return fatLevelReference;
            }

            public void setFatLevelReference(int fatLevelReference) {
                this.fatLevelReference = fatLevelReference;
            }

            public int getMuscleReference() {
                return muscleReference;
            }

            public void setMuscleReference(int muscleReference) {
                this.muscleReference = muscleReference;
            }

            public int getProteinReference() {
                return proteinReference;
            }

            public void setProteinReference(int proteinReference) {
                this.proteinReference = proteinReference;
            }

            public int getWaterContentReference() {
                return waterContentReference;
            }

            public void setWaterContentReference(int waterContentReference) {
                this.waterContentReference = waterContentReference;
            }

            public String getProtein() {
                return protein;
            }

            public void setProtein(String protein) {
                this.protein = protein;
            }

            public Object getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getUid() {
                return uid;
            }

            public void setUid(int uid) {
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

            public String getStartDate() {
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
}
