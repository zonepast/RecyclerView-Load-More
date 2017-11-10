package com.example.femmy.reloadmore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Android System on 11/9/2017.
 */

public class NewDataModel {


    @SerializedName("nAreaId")
    @Expose
    public Integer nAreaId;
    @SerializedName("nUserId")
    @Expose
    public Integer nUserId;
    @SerializedName("nCityId")
    @Expose
    public Integer nCityId;
    @SerializedName("cAreaName")
    @Expose
    public String cAreaName;
    @SerializedName("cCityName")
    @Expose
    public String cCityName;
    @SerializedName("cStateName")
    @Expose
    public String cStateName;
    @SerializedName("IsActive")
    @Expose
    public Boolean isActive;

    public NewDataModel() {
    }

    public Integer getNAreaId() {
        return nAreaId;
    }

    public void setNAreaId(Integer nAreaId) {
        this.nAreaId = nAreaId;
    }

    public Integer getNUserId() {
        return nUserId;
    }

    public void setNUserId(Integer nUserId) {
        this.nUserId = nUserId;
    }

    public Integer getNCityId() {
        return nCityId;
    }

    public void setNCityId(Integer nCityId) {
        this.nCityId = nCityId;
    }

    public String getCAreaName() {
        return cAreaName;
    }

    public void setCAreaName(String cAreaName) {
        this.cAreaName = cAreaName;
    }

    public String getCCityName() {
        return cCityName;
    }

    public void setCCityName(String cCityName) {
        this.cCityName = cCityName;
    }

    public String getCStateName() {
        return cStateName;
    }

    public void setCStateName(String cStateName) {
        this.cStateName = cStateName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
