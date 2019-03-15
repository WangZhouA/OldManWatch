package com.saiyi.oldmanwatch.entity;

/**
 * @Author liwenbo
 * @Date 18/11/12
 * @Describe
 */
public class DeviceState {
    private String electricState;
    private String sosState;
    private String scenePattern;
    private String workPattern;
    private String remindState;


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

    public String getElectricState() {
        return electricState;
    }

    public void setElectricState(String electricState) {
        this.electricState = electricState;
    }

    public String getSosState() {
        return sosState;
    }

    public void setSosState(String sosState) {
        this.sosState = sosState;
    }

    public String getRemindState() {
        return remindState;
    }

    public void setRemindState(String remindState) {
        this.remindState = remindState;
    }
}
