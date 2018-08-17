package com.aspect.workorder.config;

import org.junit.Test;

import com.aspect.workorder.config.AppConstant;

public class AppConstantTest {

	@Test
	public void test_INITIAL_QUEUE_SIZE() {
		assert AppConstant.INITIAL_QUEUE_SIZE.equals(32);
	}

	public void test_RESPONSE_HEADER_APP_DIAGNOSTIC() {
		assert AppConstant.RESPONSE_HEADER_APP_DIAGNOSTIC.equals("x-app-diagnostic");
	}

	@Test
	public void test_CONTENT_TYPE_APPLICATION_JSON() {
		assert AppConstant.CONTENT_TYPE_APPLICATION_JSON.equals("application/json");
	}

	@Test
	public void test_RESOURCE_PATH_WORK_ORDER() {
		assert AppConstant.RESOURCE_PATH_WORK_ORDER.equals("/workorder");
	}

	@Test
	public void test_WORK_ORDER_OPR_AVG_WAIT_TIME() {
		assert AppConstant.WORK_ORDER_OPR_AVG_WAIT_TIME.equals("/avgWaitTime/{currentTime}");
	}

	@Test
	public void test_WORK_ORDER_OPR_GET_POSITION() {
		assert AppConstant.WORK_ORDER_OPR_GET_POSITION.equals("/getPosition/{requesterId}");
	}

	@Test
	public void test_WORK_ORDER_OPR_REMOVE() {
		assert AppConstant.WORK_ORDER_OPR_REMOVE.equals("/remove/{requesterId}");
	}

	@Test
	public void test_WORK_ORDER_OPR_GET_ORDER_LIST() {
		assert AppConstant.WORK_ORDER_OPR_GET_ORDER_LIST.equals("/getOrderList");
	}

	@Test
	public void test_WORK_ORDER_OPR_REMOVE_NEXT_ORDER() {
		assert AppConstant.WORK_ORDER_OPR_REMOVE_NEXT_ORDER.equals("/removeNextOrder");
	}

	@Test
	public void test_WORK_ORDER_OPR_ADD_ORDER() {
		assert AppConstant.WORK_ORDER_OPR_ADD_ORDER.equals("/addOrder");
	}

	@Test
	public void test_MSG_REQUEST_WAS_NOT_ADDED_IN_THE_QUEUE() {
		assert AppConstant.MSG_REQUEST_WAS_NOT_ADDED_IN_THE_QUEUE.equals("Request was not added in the queue.");
	}

	@Test
	public void test_MSG_REQUEST_WAS_ADDED_IN_THE_QUEUE() {
		assert AppConstant.MSG_REQUEST_WAS_ADDED_IN_THE_QUEUE.equals("Request was added in the queue.");
	}

	@Test
	public void test_MSG_THE_QUEUE_IS_EMPTY() {
		assert AppConstant.MSG_THE_QUEUE_IS_EMPTY.equals("The queue is empty.");
	}

	@Test
	public void test_MSG_NO_REQUEST_WAS_FOUND_FOR_ID() {
		assert AppConstant.MSG_NO_REQUEST_WAS_FOUND_FOR_ID.equals("No request was found for id %s.");
	}

	@Test
	public void test_MSG_DEQUEUED_REQUEST_WITH_REQUESTER_ID() {
		assert AppConstant.MSG_DEQUEUED_REQUEST_WITH_REQUESTER_ID.equals("Dequeued request with requesterId=%s.");
	}

	@Test
	public void test_ERR_MSG_REQUESTER_ID_CAN_NOT_BE_NULL() {
		assert AppConstant.ERR_MSG_REQUESTER_ID_CAN_NOT_BE_NULL.equals("requesterId can not be null.");
	}

	@Test
	public void test_ERR_MSG_REQUESTER_ID_CAN_HAVE_MAXIMUM_19_DIGITS_AND_NO_DECIMALS() {
		assert AppConstant.ERR_MSG_REQUESTER_ID_CAN_HAVE_MAXIMUM_19_DIGITS_AND_NO_DECIMALS
				.equals("requesterId can have maximum 19 digits and no decimals.");
	}

	@Test
	public void test_ERR_MSG_TIME_OF_REQUEST_CAN_NOT_BE_NULL() {
		assert AppConstant.ERR_MSG_TIME_OF_REQUEST_CAN_NOT_BE_NULL.equals("timeOfRequest can not be null.");
	}

	@Test
	public void test_ERR_MSG_TIME_OF_REQUEST_CAN_HAVE_MAXIMUM_19_DIGITS_AND_NO_DECIMALS() {
		assert AppConstant.ERR_MSG_TIME_OF_REQUEST_CAN_HAVE_MAXIMUM_19_DIGITS_AND_NO_DECIMALS
				.equals("timeOfRequest can have maximum 19 digits and no decimals.");
	}

	@Test
	public void test_ERR_MSG_PROVIDE_A_VALID_NUMERIC_ARGUMENT_FOR_REQUESTER_ID() {
		assert AppConstant.ERR_MSG_PROVIDE_A_VALID_NUMERIC_ARGUMENT_FOR_REQUESTER_ID
				.equals("Provide a valid numeric argument for requesterId.");
	}

	@Test
	public void test_ERR_MSG_PROVIDE_A_VALID_NUMERIC_ARGUMENT_FOR_TIME_OF_REQUEST() {
		assert AppConstant.ERR_MSG_PROVIDE_A_VALID_NUMERIC_ARGUMENT_FOR_TIME_OF_REQUEST
				.equals("Provide a valid numeric argument for timeOfRequest.");
	}

}
