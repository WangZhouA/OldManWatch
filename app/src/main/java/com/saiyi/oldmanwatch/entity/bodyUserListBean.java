package com.saiyi.oldmanwatch.entity;

/**
 * Created by 最帅的男人 on 2019/2/23.
 */
public class bodyUserListBean {

        private String headUrl;
        private String name;
        private int    id;
        private  String  height;
        private String  sex;
        private String  birthday;
        private boolean isSelected;



    public bodyUserListBean(String headUrl, String name, int  id) {
        this.headUrl = headUrl;
        this.name = name;
        this.id = id;
    }


    public bodyUserListBean(String headUrl, String name, int id, String height, String sex, String birthday) {
        this.headUrl = headUrl;
        this.name = name;
        this.id = id;
        this.height = height;
        this.sex = sex;
        this.birthday = birthday;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public bodyUserListBean( ) {

    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
