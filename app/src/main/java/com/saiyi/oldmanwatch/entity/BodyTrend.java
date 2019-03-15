package com.saiyi.oldmanwatch.entity;

/**
 * Created by 最帅的男人 on 2019/2/21.
 */
public class BodyTrend {


    /**
     * success : true
     * code : 1000
     * message : 操作成功！
     * data : {"id":null,"uid":null,"weight":66,"score":86,"size":2,"bodyFatRate":22.3,"muscleRate":45,"waterContent":53.1,"boneSalt":4.7,"fatLevel":3,"basalMetabolism":1245,"muscle":45,"bmi":24,"createDate":1550571733000,"startDate":null,"type":null,"week":"Tuesday"}
     */

    private boolean success;
    private int code;
    private String message;
    /**
     * id : null
     * uid : null
     * weight : 66
     * score : 86
     * size : 2
     * bodyFatRate : 22.3
     * muscleRate : 45
     * waterContent : 53.1
     * boneSalt : 4.7
     * fatLevel : 3
     * basalMetabolism : 1245
     * muscle : 45
     * bmi : 24
     * createDate : 1550571733000
     * startDate : null
     * type : null
     * week : Tuesday
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private Object id;
        private Object uid;
        private double weight;
        private String score;
        private int size;
        private double bodyFatRate;
        private String muscleRate;
        private double waterContent;
        private double boneSalt;
        private String fatLevel;
        private String basalMetabolism;
        private String muscle;
        private String bmi;
        private String createDate;
        private Object startDate;
        private Object type;
        private String week;
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

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public double getBodyFatRate() {
            return bodyFatRate;
        }

        public void setBodyFatRate(double bodyFatRate) {
            this.bodyFatRate = bodyFatRate;
        }

        public String getMuscleRate() {
            return muscleRate;
        }

        public void setMuscleRate(String muscleRate) {
            this.muscleRate = muscleRate;
        }

        public double getWaterContent() {
            return waterContent;
        }

        public void setWaterContent(double waterContent) {
            this.waterContent = waterContent;
        }

        public double getBoneSalt() {
            return boneSalt;
        }

        public void setBoneSalt(double boneSalt) {
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

        public void setStartDate(Object startDate) {
            this.startDate = startDate;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }
    }
}
