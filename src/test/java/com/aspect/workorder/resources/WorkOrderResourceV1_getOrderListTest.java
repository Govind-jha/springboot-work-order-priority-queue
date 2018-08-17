package com.aspect.workorder.resources;

import java.util.Arrays;
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
public class WorkOrderResourceV1_getOrderListTest {

	@Autowired
	private TestRestTemplate restTemplate;

	/*****************************************************************************
	 * The below values are used to create request for OrderGift API, and are
	 * inserted in the order as its declaration.
	 *****************************************************************************/
	private final Long[] requesterIds = { 30L, 50L, 9L };
	private final Long[] timeOfRequest = { 300L, 500L, 900L };
	// **************************************************************************//

	@Test
	public void testGetOrderList_withEmptyQueue() {
		ResponseEntity<WorkOrderResponse> responseEntity = restTemplate.getForEntity(
				AppConstant.RESOURCE_PATH_WORK_ORDER + AppConstant.WORK_ORDER_OPR_GET_ORDER_LIST,
				WorkOrderResponse.class);
		List<String> response = responseEntity.getHeaders().get(AppConstant.RESPONSE_HEADER_APP_DIAGNOSTIC);

		assert response != null;

		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(responseEntity.getStatusCodeValue()).as("Status code is 204.").isEqualTo(204);
		softly.assertThat(response).as("Response conatains message").contains(AppConstant.MSG_THE_QUEUE_IS_EMPTY);
		softly.assertAll();

		softly.assertAll();

	}

	@Test
	public void testGetOrderList_withNonEmptyQueue() {
		int i = 0;
		for (Long requesterId : requesterIds) {
			restTemplate.postForEntity(AppConstant.RESOURCE_PATH_WORK_ORDER + AppConstant.WORK_ORDER_OPR_ADD_ORDER,
					new WorkOrderRequest(requesterId, timeOfRequest[i++]), WorkOrderResponse.class);

		}

		ResponseEntity<WorkOrderResponse> responseEntity = restTemplate.getForEntity(
				AppConstant.RESOURCE_PATH_WORK_ORDER + AppConstant.WORK_ORDER_OPR_GET_ORDER_LIST,
				WorkOrderResponse.class);
		WorkOrderResponse response = responseEntity.getBody();

		assert response != null;
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(responseEntity.getStatusCodeValue()).as("Status code is 200.").isEqualTo(200);
		softly.assertThat(response.getSuccess()).as("Successfully returned a list").isTrue();
		softly.assertThat(response.getQueue().size()).as("List has 3 ids").isEqualTo(3);
		softly.assertThat(response.getQueue().containsAll(Arrays.asList(requesterIds)))
				.as("List contains all input ids").isTrue();
		softly.assertAll();
	}

}
