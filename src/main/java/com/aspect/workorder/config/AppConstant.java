package com.aspect.workorder.config;

/**
 * This class stores all the App Constants.
 * 
 * @author kumjha
 *
 */
public class AppConstant {

	private AppConstant() {
		// Constants
	}

	/**
	 * Initial size of the priority queue.
	 */
	public static final Integer INITIAL_QUEUE_SIZE = 32;

	public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";

	public static final String RESPONSE_HEADER_APP_DIAGNOSTIC = "x-app-diagnostic";

	public static final String RESOURCE_PATH_WORK_ORDER = "/workorder";
	public static final String WORK_ORDER_OPR_AVG_WAIT_TIME = "/avgWaitTime/{currentTime}";
	public static final String WORK_ORDER_OPR_GET_POSITION = "/getPosition/{requesterId}";
	public static final String WORK_ORDER_OPR_REMOVE = "/remove/{requesterId}";
	public static final String WORK_ORDER_OPR_GET_ORDER_LIST = "/getOrderList";
	public static final String WORK_ORDER_OPR_REMOVE_NEXT_ORDER = "/removeNextOrder";
	public static final String WORK_ORDER_OPR_ADD_ORDER = "/addOrder";

	public static final String MSG_REQUEST_WAS_NOT_ADDED_IN_THE_QUEUE = "Request was not added in the queue.";
	public static final String MSG_REQUEST_WAS_ADDED_IN_THE_QUEUE = "Request was added in the queue.";
	public static final String MSG_THE_QUEUE_IS_EMPTY = "The queue is empty.";
	public static final String MSG_NO_REQUEST_WAS_FOUND_FOR_ID = "No request was found for id %s.";
	public static final String MSG_DEQUEUED_REQUEST_WITH_REQUESTER_ID = "Dequeued request with requesterId=%s.";

	public static final String ERR_MSG_REQUESTER_ID_CAN_NOT_BE_NULL = "requesterId can not be null.";
	public static final String ERR_MSG_REQUESTER_ID_CAN_HAVE_MAXIMUM_19_DIGITS_AND_NO_DECIMALS = "requesterId can have maximum 19 digits and no decimals.";
	public static final String ERR_MSG_TIME_OF_REQUEST_CAN_NOT_BE_NULL = "timeOfRequest can not be null.";
	public static final String ERR_MSG_TIME_OF_REQUEST_CAN_HAVE_MAXIMUM_19_DIGITS_AND_NO_DECIMALS = "timeOfRequest can have maximum 19 digits and no decimals.";
	public static final String ERR_MSG_PROVIDE_A_VALID_NUMERIC_ARGUMENT_FOR_REQUESTER_ID = "Provide a valid numeric argument for requesterId.";
	public static final String ERR_MSG_PROVIDE_A_VALID_NUMERIC_ARGUMENT_FOR_TIME_OF_REQUEST = "Provide a valid numeric argument for timeOfRequest.";

	public static final long TIMER_TICK_INVERVAL = 1000L;



}
