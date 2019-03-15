package com.saiyi.oldmanwatch.entity;

/**
 * @Author liwenbo
 * @Date 18/11/2
 * @Describe
 */
public class SetupBean {
    private String startDate;
    private String endDate;
    private String typeName;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "SetupBean{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
