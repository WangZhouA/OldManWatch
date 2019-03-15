package com.saiyi.oldmanwatch.entity;

/**
 * Created by 最帅的男人 on 2019/2/13.
 */
public class UserMangerBean {

    private String name;
    private int  type; //管理员还是成员
    private String phone; // 电话号码

    public UserMangerBean(String name, int type, String phone) {
        this.name = name;
        this.type = type;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserMangerBean{");
        sb.append("name='").append(name).append('\'');
        sb.append(", type=").append(type);
        sb.append(", phone='").append(phone).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
