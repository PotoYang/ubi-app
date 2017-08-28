package com.chh.yinbao.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by potoyang on 2017/8/7.
 */

@DatabaseTable(tableName = "download_info")
public class DownloadInfo implements Serializable {

    @DatabaseField(generatedId = true)
    private int mId;
    @DatabaseField
    private String url;
    @DatabaseField
    private String versionCode;
    @DatabaseField
    private String versionName;
    @DatabaseField
    private String content;
    @DatabaseField
    private String size;
    @DatabaseField
    private long downloadId;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(long downloadId) {
        this.downloadId = downloadId;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}
