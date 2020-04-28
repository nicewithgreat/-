package com.booking.pojo;

public class CourtTable0 {
    private Integer courtId;

    private String courtMaterial;

    public Integer getCourtId() {
        return courtId;
    }

    public void setCourtId(Integer courtId) {
        this.courtId = courtId;
    }

    public String getCourtMaterial() {
        return courtMaterial;
    }

    public void setCourtMaterial(String courtMaterial) {
        this.courtMaterial = courtMaterial == null ? null : courtMaterial.trim();
    }

	@Override
	public String toString() {
		return "CourtTable0 [courtId=" + courtId + ", courtMaterial=" + courtMaterial + "]";
	}
    
}