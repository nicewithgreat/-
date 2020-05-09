package com.booking.pojo;

public class TodayCourtTable5 {
    private Integer todayCourtId;

    private Integer courtId;

    private Integer timeId;

    private Integer courtState;
    
    //Á¬±í
    private CourtTable0 courtTable0;
    
    private ScheduledTimeTable1 scheduledTimeTable1;

    public Integer getTodayCourtId() {
        return todayCourtId;
    }

    public void setTodayCourtId(Integer todayCourtId) {
        this.todayCourtId = todayCourtId;
    }

    public Integer getCourtId() {
        return courtId;
    }

    public void setCourtId(Integer courtId) {
        this.courtId = courtId;
    }

    public Integer getTimeId() {
        return timeId;
    }

    public void setTimeId(Integer timeId) {
        this.timeId = timeId;
    }

    public Integer getCourtState() {
        return courtState;
    }

    public void setCourtState(Integer courtState) {
        this.courtState = courtState;
    }

	public CourtTable0 getCourtTable0() {
		return courtTable0;
	}

	public void setCourtTable0(CourtTable0 courtTable0) {
		this.courtTable0 = courtTable0;
	}

	public ScheduledTimeTable1 getScheduledTimeTable1() {
		return scheduledTimeTable1;
	}

	public void setScheduledTimeTable1(ScheduledTimeTable1 scheduledTimeTable1) {
		this.scheduledTimeTable1 = scheduledTimeTable1;
	}

	@Override
	public String toString() {
		return "TodayCourtTable5 [todayCourtId=" + todayCourtId + ", courtId=" + courtId + ", timeId=" + timeId
				+ ", courtState=" + courtState + ", courtMaterial=" + courtTable0.getCourtMaterial() + ", starttime="
				+ scheduledTimeTable1.getStarttime() + ", endtime=" + scheduledTimeTable1.getEndtime() + "]";
	}
    
	
    
}