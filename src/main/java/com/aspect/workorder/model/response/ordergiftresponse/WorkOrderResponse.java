package com.aspect.workorder.model.response.ordergiftresponse;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * This class is a POJO containing all possible response fields.
 * 
 * @author kumjha
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class WorkOrderResponse {

	private Long requesterID;
	private Double avgTime;
	private List<Long> queue;
	private Integer position;
	private Boolean success;
	private String message;

	public WorkOrderResponse() {
	}

	public Long getRequesterID() {
		return requesterID;
	}

	public void setRequesterID(Long requesterID) {
		this.requesterID = requesterID;
	}

	public Double getAvgTime() {
		return avgTime;
	}

	public void setAvgTime(Double avgTime) {
		this.avgTime = avgTime;
	}

	public List<Long> getQueue() {
		return queue;
	}

	public void setQueue(List<Long> queue) {
		this.queue = queue;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
