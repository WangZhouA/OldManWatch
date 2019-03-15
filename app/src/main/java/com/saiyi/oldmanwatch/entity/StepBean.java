package com.saiyi.oldmanwatch.entity;

import java.util.List;

/**
 * @Author liwenbo
 * @Date 18/11/5
 * @Describe
 */
public class StepBean {
    private String dayStep;
    private List<StepsBean> steps;

    public class StepsBean {
        String date;
        String steps;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getSteps() {
            return steps;
        }

        public void setSteps(String steps) {
            this.steps = steps;
        }

        @Override
        public String toString() {
            return "StepsBean{" +
                    "date='" + date + '\'' +
                    ", step='" + steps + '\'' +
                    '}';
        }
    }

    public String getDayStep() {
        return dayStep;
    }

    public void setDayStep(String dayStep) {
        this.dayStep = dayStep;
    }

    public List<StepsBean> getSteps() {
        return steps;
    }

    public void setSteps(List<StepsBean> steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "dayStep='" + dayStep + '\'' +
                ", steps=" + steps +
                '}';
    }


}
