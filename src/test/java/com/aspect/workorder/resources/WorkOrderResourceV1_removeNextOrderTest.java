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
public class WorkOrderResourceV1_removeNextOrderTest {

	@Autowired
	private TestRestTemplate restTemplate;

	private final Long requesterId_1 = 30L;
	private final Long timeOfRequest_1 = 300L;

	@Test
	public void testRemoveNextOrder_withEmptyQueue() {
		ResponseEntity<WorkOrderResponse> responseEntity = restTemplate.getForEntity(
				AppConstant.RESOURCE_PATH_WORK_ORDER + AppConstant.WORK_ORDER_OPR_REMOVE_NEXT_ORDER,
				WorkOrderResponse.class);
		
		List<String> response = responseEntity.getHeaders().get(AppConstant.RESPONSE_HEADER_APP_DIAGNOSTIC);

		assert response != null;
		
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(responseEntity.getStatusCodeValue()).as("Status code is 204.").isEqualTo(204);
		softly.assertThat(response).as("Response conatains message").contains(AppConstant.MSG_THE_QUEUE_IS_EMPTY);
		softly.assertAll();
	}

	@Test
	public void testRemoveNextOrder_withNonEmptyQueue() {
		restTemplate.postForEntity(AppConstant.RESOURCE_PATH_WORK_ORDER + AppConstant.WORK_ORDER_OPR_ADD_ORDER,
				new WorkOrderRequest(requesterId_1, timeOfRequest_1), WorkOrderResponse.class);

		ResponseEntity<WorkOrderResponse> responseEntity = restTemplate.getForEntity(
				AppConstant.RESOURCE_PATH_WORK_ORDER + AppConstant.WORK_ORDER_OPR_REMOVE_NEXT_ORDER,
				WorkOrderResponse.class);
		
		WorkOrderResponse response = responseEntity.getBody();

		assert response != null;
		
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(responseEntity.getStatusCodeValue()).as("Status code is 200.").isEqualTo(200);
		softly.assertThat(response.getSuccess()).as("Get the first element in the queue").isTrue();
		softly.assertThat(response.getRequesterID()).as("Requestor ID").isEqualTo(requesterId_1);
		softly.assertAll();
	}

}
