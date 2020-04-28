package com.booking.pojo;

import java.util.Date;

public class ApplyFixedTable7 {
    private Integer applyFixedId;

    private Integer userId;

    private Date applyTime;

    private Integer timeId;

    private Integer dayOfWeek;

    private Integer applyState;

    public Integer getApplyFixedId() {
        return applyFixedId;
    }

    public void setApplyFixedId(Integer applyFixedId) {
        this.applyFixedId = applyFixedId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getTimeId() {
        return timeId;
    }

    public void setTimeId(Integer timeId) {
        this.timeId = timeId;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getApplyState() {
        return applyState;
    }

    public void setApplyState(Integer applyState) {
        this.applyState = applyState;
    }
}