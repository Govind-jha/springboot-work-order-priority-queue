package com.aspect.workorder.resources;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.aspect.workorder.config.AppConstant;
import com.aspect.workorder.model.request.ordergiftrequest.WorkOrderRequest;
import com.aspect.workorder.model.response.ordergiftresponse.WorkOrderResponse;

@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WorkOrderResourceV1_addOrderTest {

	@Autowired
	private TestRestTemplate restTemplate;

	/*****************************************************************************
	 * The below values are used to create request for OrderGift API, and are
	 * inserted in the order as its declaration.
	 *****************************************************************************/
	private final Long requesterId_1 = 30L;
	private final Long timeOfRequest_1 = 300L;

	private final Long requesterId_2 = 50L;
	private final Long timeOfRequest_2 = 500L;

	private final Long requesterId_3 = 7L;
	private final Long timeOfRequest_3 = 700L;

	private final Long requesterId_4 = 9L;
	private final Long timeOfRequest_4 = 900L;
	// **************************************************************************//

	@Test
	public void testAddOrder_withOneRequest() {
		ResponseEntity<WorkOrderResponse> responseEntity = restTemplate.postForEntity(
				AppConstant.RESOURCE_PATH_WORK_ORDER + AppConstant.WORK_ORDER_OPR_ADD_ORDER,
				new WorkOrderRequest(requesterId_1, timeOfRequest_1), WorkOrderResponse.class);
		WorkOrderResponse response = responseEntity.getBody();

		assert response != null;

		SoftAssertions softly = new SoftAssertions();
		softly.assertThat(responseEntity.getStatusCodeValue()).as("Status code is 200.").isEqualTo(201);
		softly.assertThat(response.getSuccess()).as("Element was added in the queue").isTrue();
		softly.assertThat(response.getMessage()).as("Request was added in the queue.")
				.isEqualTo(AppConstant.MSG_REQUEST_WAS_ADDED_IN_THE_QUEUE);
		softly.assertAll();
	}

	@Test
	public void testAddOrder_withTwoRequestWithDifferentIds() {
		ResponseEntity<WorkOrderResponse> responseEntity_1 = restTemplate.postForEntity(
				AppConstant.RESOURCE_PATH_WORK_ORDER + AppConstant.WORK_ORDER_OPR_ADD_ORDER,
				new WorkOrderRequest(requesterId_2, timeOfRequest_2), WorkOrderResponse.class);
		WorkOrderResponse response1 = responseEntity_1.getBody();

		ResponseEntity<WorkOrderResponse> responseEntity_2 = restTemplate.postForEntity(
				AppConstant.RESOURCE_PATH_WORK_ORDER + AppConstant.WORK_ORDER_OPR_ADD_ORDER,
				new WorkOrderRequest(requesterId_3, timeOfRequest_3), WorkOrderResponse.class);
		WorkOrderResponse response2 = responseEntity_2.getBody();

		assert response1 != null && response2 != null;

		SoftAssertions softly = new SoftAssertions();
		softly.assertThat(responseEntity_1.getStatusCodeValue()).as("Status code is 200.").isEqualTo(201);
		softly.assertThat(response1.getSuccess()).as("Request 1 was added in the queue").isTrue();
		softly.assertThat(response1.getMessage()).as("Response 1 contains success message")
				.isEqualTo(AppConstant.MSG_REQUEST_WAS_ADDED_IN_THE_QUEUE);

		softly.assertThat(responseEntity_2.getStatusCodeValue()).as("Status code is 200.").isEqualTo(201);
		softly.assertThat(response2.getSuccess()).as("Request 2 was added in the queue").isTrue();
		softly.assertThat(response2.getMessage()).as("Request 2 contains success message")
				.isEqualTo(AppConstant.MSG_REQUEST_WAS_ADDED_IN_THE_QUEUE);

		softly.assertAll();
	}

	@Test
	public void testAddOrder_twoRequestFromSameId() {
		ResponseEntity<WorkOrderResponse> responseEntity_1 = restTemplate.postForEntity(
				AppConstant.RESOURCE_PATH_WORK_ORDER + AppConstant.WORK_ORDER_OPR_ADD_ORDER,
				new WorkOrderRequest(requesterId_4, timeOfRequest_4), WorkOrderResponse.class);
		WorkOrderResponse response1 = responseEntity_1.getBody();

		ResponseEntity<WorkOrderResponse> responseEntity_2 = restTemplate.postForEntity(
				AppConstant.RESOURCE_PATH_WORK_ORDER + AppConstant.WORK_ORDER_OPR_ADD_ORDER,
				new WorkOrderRequest(requesterId_4, timeOfRequest_4), WorkOrderResponse.class);
		
		List<String> response2 = responseEntity_2.getHeaders().get(AppConstant.RESPONSE_HEADER_APP_DIAGNOSTIC);

		assert response1 != null && response2 != null;

		SoftAssertions softly = new SoftAssertions();
		softly.assertThat(responseEntity_1.getStatusCodeValue()).as("Status code is 200.").isEqualTo(201);
		softly.assertThat(response1.getSuccess()).as("Request 1 was added in the queue").isTrue();
		softly.assertThat(response1.getMessage()).as("Response 1 contains success message")
				.isEqualTo(AppConstant.MSG_REQUEST_WAS_ADDED_IN_THE_QUEUE);

		softly.assertThat(responseEntity_2.getStatusCodeValue()).as("Status code is 204.").isEqualTo(204);
		softly.assertThat(response2).as("Response conatains message").contains(AppConstant.MSG_REQUEST_WAS_NOT_ADDED_IN_THE_QUEUE);

		softly.assertAll();
	}

}
