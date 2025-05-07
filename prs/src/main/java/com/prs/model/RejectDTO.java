package com.prs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RejectDTO {
	@JsonProperty("reason")
	String reasonForRejection;

	public String getReasonForRejection() {
		return reasonForRejection;
	}

	public void setReasonForRejection(String reason) {
		reasonForRejection = reason;
	}
	
}
