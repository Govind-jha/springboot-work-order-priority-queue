package com.aspect.workorder.resources;

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
public class WorkOrderResourceV1_avgWaitTimeTest {

	@Autowired
	private TestRestTemplate restTemplate;

	/*****************************************************************************
	 * The below values are used to create request for OrderGift API, and are
	 * inserted in the order as its declaration.
	 *****************************************************************************/
	private final Long[] requesterIds = { 30L, 50L, 9L };
	private final Long[] timeOfRequest = { 3_000_000L, 5_000_000L, 9_000_000L };

	private final Long timeOfRef = 100_00_000L;
	// **************************************************************************//

	@Test
	public void testAvgWaitTime_withEmptyQueue() {
		ResponseEntity<WorkOrderResponse> responseEntity = restTemplate.getForEntity(
				AppConstant.RESOURCE_PATH_WORK_ORDER
						+ AppConstant.WORK_ORDER_OPR_AVG_WAIT_TIME.replaceFirst("\\{.*\\}", timeOfRef.toString()),
				WorkOrderResponse.class);
		WorkOrderResponse response = responseEntity.getBody();

		assert response != null;
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(responseEntity.getStatusCodeValue()).as("Request executed successfully with status code 200.")
				.isEqualTo(200);
		softly.assertThat(response.getAvgTime()).as("Average wait time is zero for empty queue.").isEqualTo(0);
		softly.assertThat(response.getSuccess()).as("Failed to find id in queue").isTrue();
		softly.assertAll();
	}

	@Test
	public void testAvgWaitTime_withOneElementQueue() {

		restTemplate.postForEntity(AppConstant.RESOURCE_PATH_WORK_ORDER + AppConstant.WORK_ORDER_OPR_ADD_ORDER,
				new WorkOrderRequest(requesterIds[0], timeOfRequest[0]), WorkOrderResponse.class);

		ResponseEntity<WorkOrderResponse> responseEntity = restTemplate.getForEntity(
				AppConstant.RESOURCE_PATH_WORK_ORDER
						+ AppConstant.WORK_ORDER_OPR_AVG_WAIT_TIME.replaceFirst("\\{.*\\}", timeOfRef.toString()),
				WorkOrderResponse.class);
		WorkOrderResponse response = responseEntity.getBody();

		assert response != null;
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(responseEntity.getStatusCodeValue()).as("Status code is 200.").isEqualTo(200);
		softly.assertThat(response.getAvgTime()).as("Average wait time is not zero with one element.").isEqualTo(7000d);
		softly.assertThat(response.getSuccess()).as("Success is true.").isTrue();
		softly.assertAll();
	}

	@Test
	public void testAvgWaitTime_withNonEmptyQueue() {
		restTemplate.postForEntity(AppConstant.RESOURCE_PATH_WORK_ORDER + AppConstant.WORK_ORDER_OPR_ADD_ORDER,
				new WorkOrderRequest(requesterIds[1], timeOfRequest[1]), WorkOrderResponse.class);
		
		restTemplate.postForEntity(AppConstant.RESOURCE_PATH_WORK_ORDER + AppConstant.WORK_ORDER_OPR_ADD_ORDER,
				new WorkOrderRequest(requesterIds[2], timeOfRequest[2]), WorkOrderResponse.class);

		ResponseEntity<WorkOrderResponse> responseEntity = restTemplate.getForEntity(
				AppConstant.RESOURCE_PATH_WORK_ORDER
						+ AppConstant.WORK_ORDER_OPR_AVG_WAIT_TIME.replaceFirst("\\{.*\\}", timeOfRef.toString()),
				WorkOrderResponse.class);
		WorkOrderResponse response = responseEntity.getBody();

		assert response != null;
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(responseEntity.getStatusCodeValue()).as("Status code is 200.").isEqualTo(200);
		softly.assertThat(response.getAvgTime()).as("Average wait time is not zero for non empty queue.").isGreaterThan(4333d);
		softly.assertThat(response.getAvgTime()).as("Average wait time is not zero for non empty queue.").isLessThan(4334d);
		softly.assertThat(response.getSuccess()).as("Success is true.").isTrue();
		softly.assertAll();
	}

}
