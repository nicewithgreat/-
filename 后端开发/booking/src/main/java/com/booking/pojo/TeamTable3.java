package com.booking.pojo;

public class TeamTable3 {
    private Integer teamId;

    private String college;

    private Integer applyFixedId;

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college == null ? null : college.trim();
    }

    public Integer getApplyFixedId() {
        return applyFixedId;
    }

    public void setApplyFixedId(Integer applyFixedId) {
        this.applyFixedId = applyFixedId;
    }
}