package com.saiyi.oldmanwatch.entity;

/**
 * @Author liwenbo
 * @Date 18/11/10
 * @Describe
 */
public class DeviceDetailsBean {
    private String address;
    private String headUrl;
    private HeartPressureBean heartPressure;
    private StepNumberBean stepNumber;

    public class HeartPressureBean {
        private String heart;
        private String heartDate;
        private String bloodDate;
        private String pressure;

        public String getHeart() {
            return heart;
        }

        public void setHeart(String heart) {
            this.heart = heart;
        }

        public String getHeartDate() {
            return heartDate;
        }

        public void setHeartDate(String heartDate) {
            this.heartDate = heartDate;
        }

        public String getBloodDate() {
            return bloodDate;
        }

        public void setBloodDate(String bloodDate) {
            this.bloodDate = bloodDate;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }
    }

    public class StepNumberBean {
        private String steps;
        private String stepsDate;

        public String getSteps() {
            return steps;
        }

        public void setSteps(String steps) {
            this.steps = steps;
        }

        public String getStepsDate() {
            return stepsDate;
        }

        public void setStepsDate(String stepsDate) {
            this.stepsDate = stepsDate;
        }
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getSleepDate() {
        return sleepDate;
    }

    public void setSleepDate(String sleepDate) {
        this.sleepDate = sleepDate;
    }

    public String getTotalSleep() {
        return totalSleep;
    }

    public void setTotalSleep(String totalSleep) {
        this.totalSleep = totalSleep;
    }

    private String sleepDate;

    private String totalSleep;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public HeartPressureBean getHeartPressure() {
        return heartPressure;
    }

    public void setHeartPressure(HeartPressureBean heartPressure) {
        this.heartPressure = heartPressure;
    }

    public StepNumberBean getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(StepNumberBean stepNumber) {
        this.stepNumber = stepNumber;
    }
}
