package com.chh.yinbao;

import java.io.Serializable;

/**
 * Created by potoyang on 2017/8/10.
 */

public class User implements Serializable {

    private String carNo;
    private int comeFrom;
    private String craeteTime;
    private int id;
    private String idCard;
    private String mobile;
    private String name;
    private String nickName;
    private String pic;
    private int sex;
    private int status;
    private String weixinNickName;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public int getComeFrom() {
        return comeFrom;
    }

    public void setComeFrom(int comeFrom) {
        this.comeFrom = comeFrom;
    }

    public String getCraeteTime() {
        return craeteTime;
    }

    public void setCraeteTime(String craeteTime) {
        this.craeteTime = craeteTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWeixinNickName() {
        return weixinNickName;
    }

    public void setWeixinNickName(String weixinNickName) {
        this.weixinNickName = weixinNickName;
    }
}

