package com.chh.yinbao.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by potoyang on 2017/8/7.
 */

@DatabaseTable(tableName = "warning_info")
public class WarningInfoTb implements Serializable {

    @DatabaseField(generatedId = true)
    private int mId;
    @DatabaseField
    private String wId;
    @DatabaseField
    private String warningTime;
    @DatabaseField
    private String warningDesc;
    @DatabaseField
    private String warningValue;
    @DatabaseField
    private String accountId;
    @DatabaseField
    private String carId;
    @DatabaseField
    private String type;
    @DatabaseField
    private String bak1;
    @DatabaseField
    private String bak2;

    public WarningInfoTb() {
    }


    public String getBak2() {
        return bak2;
    }

    public void setBak2(String bak2) {
        this.bak2 = bak2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBak1() {
        return bak1;
    }

    public void setBak1(String bak1) {
        this.bak1 = bak1;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getWarningTime() {
        return warningTime;
    }

    public void setWarningTime(String warningTime) {
        this.warningTime = warningTime;
    }

    public String getWarningDesc() {
        return warningDesc;
    }

    public void setWarningDesc(String warningDesc) {
        this.warningDesc = warningDesc;
    }

    public String getWarningValue() {
        return warningValue;
    }

    public void setWarningValue(String warningValue) {
        this.warningValue = warningValue;
    }

    public String getwId() {
        return wId;
    }

    public void setwId(String wId) {
        this.wId = wId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

}
