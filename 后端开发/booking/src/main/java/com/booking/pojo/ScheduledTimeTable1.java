package com.booking.pojo;

import java.util.Date;

public class ScheduledTimeTable1 {
    private Integer timeId;

    private Date starttime;

    private Date endtime;

    public Integer getTimeId() {
        return timeId;
    }

    public void setTimeId(Integer timeId) {
        this.timeId = timeId;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

	@Override
	public String toString() {
		return "ScheduledTimeTable1 [timeId=" + timeId + ", starttime=" + starttime + ", endtime=" + endtime + "]";
	}
}