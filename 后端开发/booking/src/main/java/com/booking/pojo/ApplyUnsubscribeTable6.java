package com.booking.pojo;

import java.util.Date;

public class ApplyUnsubscribeTable6 {
    private Integer unsubscribeId;

    private Integer userId;

    private Integer bookId;

    private Date applyTime;

    private String reason;

    private Integer unsubscribeState;

    public Integer getUnsubscribeId() {
        return unsubscribeId;
    }

    public void setUnsubscribeId(Integer unsubscribeId) {
        this.unsubscribeId = unsubscribeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Integer getUnsubscribeState() {
        return unsubscribeState;
    }

    public void setUnsubscribeState(Integer unsubscribeState) {
        this.unsubscribeState = unsubscribeState;
    }
}