package com.aspect.workorder.resources;

import java.text.DecimalFormat;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aspect.workorder.config.AppConstant;
import com.aspect.workorder.model.request.ordergiftrequest.WorkOrderRequest;
import com.aspect.workorder.model.response.ordergiftresponse.WorkOrderResponse;
import com.aspect.workorder.services.WorkOrderServiceImpl;

@RestController
@RequestMapping(AppConstant.RESOURCE_PATH_WORK_ORDER)
public class WorkOrderResourceV1 implements WorkOrderResource {

	@Autowired
	WorkOrderServiceImpl giftOrdersService;

	@RequestMapping(value = AppConstant.WORK_ORDER_OPR_ADD_ORDER, method = RequestMethod.POST, produces = {
			AppConstant.CONTENT_TYPE_APPLICATION_JSON }, consumes = { AppConstant.CONTENT_TYPE_APPLICATION_JSON })
	public ResponseEntity<WorkOrderResponse> addOrder(@RequestBody @Valid final WorkOrderRequest request) {

		final WorkOrderResponse response = new WorkOrderResponse();
		final Boolean successStatus = giftOrdersService.addOrder(request.getRequesterId(), request.getTimeOfRequest());

		if (successStatus) {

			response.setSuccess(successStatus);
			response.setMessage(AppConstant.MSG_REQUEST_WAS_ADDED_IN_THE_QUEUE);

		} else {

			response.setMessage(AppConstant.MSG_REQUEST_WAS_NOT_ADDED_IN_THE_QUEUE);
			return ResponseEntity.noContent().header(AppConstant.RESPONSE_HEADER_APP_DIAGNOSTIC, response.getMessage())
					.build();

		}

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@RequestMapping(value = AppConstant.WORK_ORDER_OPR_REMOVE_NEXT_ORDER, method = RequestMethod.GET, produces = {
			AppConstant.CONTENT_TYPE_APPLICATION_JSON })
	public ResponseEntity<WorkOrderResponse> removeNextOrder() {

		final WorkOrderResponse response = new WorkOrderResponse();
		final Long requesterId = giftOrdersService.removeNextOrder();

		if (requesterId != -1L) {

			response.setRequesterID(requesterId);
			response.setSuccess(Boolean.TRUE);

		} else {

			response.setMessage(AppConstant.MSG_THE_QUEUE_IS_EMPTY);
			return ResponseEntity.noContent().header(AppConstant.RESPONSE_HEADER_APP_DIAGNOSTIC, response.getMessage())
					.build();

		}

		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = AppConstant.WORK_ORDER_OPR_GET_ORDER_LIST, method = RequestMethod.GET, produces = {
			AppConstant.CONTENT_TYPE_APPLICATION_JSON })
	public ResponseEntity<WorkOrderResponse> getOrderList() {

		final WorkOrderResponse response = new WorkOrderResponse();
		final List<Long> orderList = giftOrdersService.getOrderList();

		if (!orderList.isEmpty()) {

			response.setSuccess(Boolean.TRUE);
			response.setQueue(orderList);
			return ResponseEntity.ok(response);

		}

		response.setMessage(AppConstant.MSG_THE_QUEUE_IS_EMPTY);
		return ResponseEntity.noContent().header(AppConstant.RESPONSE_HEADER_APP_DIAGNOSTIC, response.getMessage())
				.build();

	}

	@RequestMapping(value = AppConstant.WORK_ORDER_OPR_REMOVE, method = RequestMethod.DELETE, produces = {
			AppConstant.CONTENT_TYPE_APPLICATION_JSON })
	public ResponseEntity<Void> remove(
			@PathVariable("requesterId") @Valid @NotNull(message = AppConstant.ERR_MSG_REQUESTER_ID_CAN_NOT_BE_NULL) @Pattern(regexp = "[0-9]+", message = AppConstant.ERR_MSG_PROVIDE_A_VALID_NUMERIC_ARGUMENT_FOR_REQUESTER_ID) @Min(1L) @Max(9223372036854775807L) final Long requesterId) {

		final Long result = giftOrdersService.remove(requesterId);

		if (result.toString().equals(requesterId.toString())) {

			return ResponseEntity.status(HttpStatus.RESET_CONTENT)
					.header(AppConstant.RESPONSE_HEADER_APP_DIAGNOSTIC,
							String.format(AppConstant.MSG_DEQUEUED_REQUEST_WITH_REQUESTER_ID, requesterId.toString()))
					.build();

		}

		return ResponseEntity.noContent().header(AppConstant.RESPONSE_HEADER_APP_DIAGNOSTIC,
				String.format(AppConstant.MSG_NO_REQUEST_WAS_FOUND_FOR_ID, requesterId)).build();

	}

	@RequestMapping(value = AppConstant.WORK_ORDER_OPR_GET_POSITION, method = RequestMethod.GET, produces = {
			AppConstant.CONTENT_TYPE_APPLICATION_JSON })
	public ResponseEntity<WorkOrderResponse> getPosition(
			@PathVariable("requesterId") @Valid @NotNull(message = AppConstant.ERR_MSG_REQUESTER_ID_CAN_NOT_BE_NULL) @Pattern(regexp = "[0-9]+", message = AppConstant.ERR_MSG_PROVIDE_A_VALID_NUMERIC_ARGUMENT_FOR_REQUESTER_ID) @Min(1L) @Max(9223372036854775807L) final Long requesterId) {

		final Integer position = giftOrdersService.getPosition(requesterId);
		final WorkOrderResponse response = new WorkOrderResponse();

		if (position != -1) {

			response.setPosition(position + 1);
			response.setSuccess(Boolean.TRUE);

		} else {

			return ResponseEntity.noContent().header(AppConstant.RESPONSE_HEADER_APP_DIAGNOSTIC,
					String.format(AppConstant.MSG_NO_REQUEST_WAS_FOUND_FOR_ID, requesterId)).build();

		}

		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = AppConstant.WORK_ORDER_OPR_AVG_WAIT_TIME, method = RequestMethod.GET, produces = {
			AppConstant.CONTENT_TYPE_APPLICATION_JSON })
	public ResponseEntity<WorkOrderResponse> avgWaitTime(
			@PathVariable("currentTime") @Valid @NotNull(message = AppConstant.ERR_MSG_TIME_OF_REQUEST_CAN_NOT_BE_NULL) @Pattern(regexp = "[0-9]+", message = AppConstant.ERR_MSG_PROVIDE_A_VALID_NUMERIC_ARGUMENT_FOR_TIME_OF_REQUEST) @Min(1L) @Max(9223372036854775807L) Long currentTime) {

		final Double avgWaitTime = giftOrdersService.avgWaitTime(currentTime / 1000);
		final WorkOrderResponse response = new WorkOrderResponse();
		final DecimalFormat df2 = new DecimalFormat(".##");

		response.setSuccess(Boolean.TRUE);
		response.setAvgTime(Double.valueOf(df2.format(avgWaitTime)));

		return ResponseEntity.ok(response);
	}

}
