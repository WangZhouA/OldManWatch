package com.saiyi.oldmanwatch.entity;

/**
 * @Author liwenbo
 * @Date 18/10/19
 * @Describe
 */
public class AddOpinionBean {
    private int uid;
    private String imgUrl;
    private String contents;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "AddOpinionBean{" +
                "uid=" + uid +
                ", imgUrl='" + imgUrl + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
