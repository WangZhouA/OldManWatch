package com.saiyi.oldmanwatch.entity;

/**
 * Created by 最帅的男人 on 2019/2/27.
 */
public class TrendDateBean {

    private int id;
    private int uid;
    private String weight;
    private int score;
    private int size;
    private String bodyFatRate;
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


    public TrendDateBean(int id, int uid, String weight, int score, int size, String bodyFatRate, int muscleRate, int waterContent, int boneSalt, int fatLevel, int basalMetabolism, int muscle, int bmi, String createDate, String startDate, String type, String week, long start, long end, String month) {
        this.id = id;
        this.uid = uid;
        this.weight = weight;
        this.score = score;
        this.size = size;
        this.bodyFatRate = bodyFatRate;
        this.muscleRate = muscleRate;
        this.waterContent = waterContent;
        this.boneSalt = boneSalt;
        this.fatLevel = fatLevel;
        this.basalMetabolism = basalMetabolism;
        this.muscle = muscle;
        this.bmi = bmi;
        this.createDate = createDate;
        this.startDate = startDate;
        this.type = type;
        this.week = week;
        this.start = start;
        this.end = end;
        this.month = month;
    }


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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getBodyFatRate() {
        return bodyFatRate;
    }

    public void setBodyFatRate(String bodyFatRate) {
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
