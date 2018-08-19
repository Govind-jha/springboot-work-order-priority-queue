package com.aspect.workorder.model.response.ordergiftresponse;

import java.util.List;

import com.aspect.workorder.model.servicerequest.ServiceRequest;
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

	private Double avgTime;
	private List<ServiceRequest> queue;
	private ServiceRequest serviceRequest;
	private Integer position;
	private String message;

	public WorkOrderResponse() {
	}

	public Double getAvgTime() {
		return avgTime;
	}

	public void setAvgTime(Double avgTime) {
		this.avgTime = avgTime;
	}

	public List<ServiceRequest> getQueue() {
		return queue;
	}

	public void setQueue(List<ServiceRequest> queue) {
		this.queue = queue;
	}

	public ServiceRequest getServiceRequest() {
		return serviceRequest;
	}

	public void setServiceRequest(ServiceRequest serviceRequest) {
		this.serviceRequest = serviceRequest;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
