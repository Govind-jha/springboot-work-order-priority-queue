package com.aspect.workorder.model.request.ordergiftrequest;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.aspect.workorder.config.AppConstant;

@Valid
public class WorkOrderRequest {

	@NotNull(message = AppConstant.ERR_MSG_REQUESTER_ID_CAN_NOT_BE_NULL)
	@Digits(fraction = 0, integer = 19, message = AppConstant.ERR_MSG_REQUESTER_ID_CAN_HAVE_MAXIMUM_19_DIGITS_AND_NO_DECIMALS)
	@Range(min = 1L, max = 9223372036854775807L, message = AppConstant.ERR_MSG_PROVIDE_A_VALID_NUMERIC_ARGUMENT_FOR_REQUESTER_ID)
	private Long requesterId;

	@NotNull(message = AppConstant.ERR_MSG_TIME_OF_REQUEST_CAN_NOT_BE_NULL)
	@Digits(fraction = 0, integer = 19, message = AppConstant.ERR_MSG_TIME_OF_REQUEST_CAN_HAVE_MAXIMUM_19_DIGITS_AND_NO_DECIMALS)
	@Range(min = 1L, max = 9223372036854775807L, message = AppConstant.ERR_MSG_PROVIDE_A_VALID_NUMERIC_ARGUMENT_FOR_TIME_OF_REQUEST)
	private Long timeOfRequest;

	public WorkOrderRequest(Long requesterId, Long timeOfRequest) {
		this.requesterId = requesterId;
		this.timeOfRequest = timeOfRequest;
	}

	public Long getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(Long requesterId) {
		this.requesterId = requesterId;
	}

	public Long getTimeOfRequest() {
		return timeOfRequest;
	}

	public void setTimeOfRequest(Long timeOfRequest) {
		this.timeOfRequest = timeOfRequest;
	}
}
