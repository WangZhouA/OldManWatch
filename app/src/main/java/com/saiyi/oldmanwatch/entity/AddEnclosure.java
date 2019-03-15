package com.saiyi.oldmanwatch.entity;

/**
 * @Author liwenbo
 * @Date 18/11/9
 * @Describe
 */
public class AddEnclosure {
    private String e;
    private String n;
    private String mac;
    private String name;
    private String range;

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    @Override
    public String toString() {
        return "AddEnclosure{" +
                "e='" + e + '\'' +
                ", n='" + n + '\'' +
                ", mac='" + mac + '\'' +
                ", name='" + name + '\'' +
                ", range='" + range + '\'' +
                '}';
    }
}
