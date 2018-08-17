package com.aspect.workorder.resources;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
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
public class WorkOrderResourceV1_removeTest {

	@Autowired
	private TestRestTemplate restTemplate;

	/*****************************************************************************
	 * The below values are used to create request for OrderGift API, and are
	 * inserted in the order as its declaration.
	 *****************************************************************************/
	private final Long[] requesterIds = { 30L, 50L, 9L };
	private final Long[] timeOfRequest = { 300L, 500L, 900L };
	private final Long requesterId1 = 19L;
	private final Long requesterId2 = 35L;
	// **************************************************************************//

	@Before
	public void setUp() {
		int i = 0;
		for (Long requesterId : requesterIds) {
			restTemplate.postForEntity(AppConstant.RESOURCE_PATH_WORK_ORDER + AppConstant.WORK_ORDER_OPR_ADD_ORDER,
					new WorkOrderRequest(requesterId, timeOfRequest[i++]), WorkOrderResponse.class);

		}
	}

	@Test
	public void testRemove_withIdPresentInQueue() {

		restTemplate.delete(AppConstant.RESOURCE_PATH_WORK_ORDER
				+ AppConstant.WORK_ORDER_OPR_REMOVE.replaceFirst("\\{.*\\}", requesterIds[0].toString()));

		ResponseEntity<WorkOrderResponse> responseEntity = restTemplate.getForEntity(
				AppConstant.RESOURCE_PATH_WORK_ORDER + AppConstant.WORK_ORDER_OPR_GET_ORDER_LIST,
				WorkOrderResponse.class);
		WorkOrderResponse response = responseEntity.getBody();

		assert response != null;
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(responseEntity.getStatusCodeValue()).as("Request executed successfully with status code 200.")
				.isEqualTo(200);
		softly.assertThat(response.getQueue().contains(requesterIds[0])).as("List do not contain requestor id.")
				.isFalse();
		softly.assertAll();
	}

	@Test
	public void testRemove_withIdNotPresentInQueue() {
		restTemplate.delete(AppConstant.RESOURCE_PATH_WORK_ORDER
				+ AppConstant.WORK_ORDER_OPR_REMOVE.replaceFirst("\\{.*\\}", requesterId1.toString()));

		restTemplate.delete(AppConstant.RESOURCE_PATH_WORK_ORDER
				+ AppConstant.WORK_ORDER_OPR_REMOVE.replaceFirst("\\{.*\\}", requesterId2.toString()));

		restTemplate.delete(AppConstant.RESOURCE_PATH_WORK_ORDER
				+ AppConstant.WORK_ORDER_OPR_REMOVE.replaceFirst("\\{.*\\}", requesterId2.toString()));

		ResponseEntity<WorkOrderResponse> responseEntity = restTemplate.getForEntity(
				AppConstant.RESOURCE_PATH_WORK_ORDER + AppConstant.WORK_ORDER_OPR_GET_ORDER_LIST,
				WorkOrderResponse.class);
		WorkOrderResponse response = responseEntity.getBody();

		assert response != null;
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(responseEntity.getStatusCodeValue()).as("Request executed successfully with status code 200.")
				.isEqualTo(200);
		softly.assertThat(response.getQueue().contains(requesterId1)).as("List do not contain requestor id.").isFalse();
		softly.assertAll();
	}

	@Test(expected = Exception.class)
	public void testRemove_withIdNull() {
		restTemplate.delete(AppConstant.RESOURCE_PATH_WORK_ORDER
				+ AppConstant.WORK_ORDER_OPR_REMOVE.replaceFirst("\\{.*\\}", null));

		restTemplate.delete(AppConstant.RESOURCE_PATH_WORK_ORDER
				+ AppConstant.WORK_ORDER_OPR_REMOVE.replaceFirst("\\{.*\\}", null));

		ResponseEntity<WorkOrderResponse> responseEntity = restTemplate.getForEntity(
				AppConstant.RESOURCE_PATH_WORK_ORDER + AppConstant.WORK_ORDER_OPR_GET_ORDER_LIST,
				WorkOrderResponse.class);
		WorkOrderResponse response = responseEntity.getBody();

		assert response != null;
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(responseEntity.getStatusCodeValue()).as("Request executed successfully with status code 200.")
				.isEqualTo(200);
		softly.assertThat(response.getQueue().contains(requesterId1)).as("List do not contain requestor id.").isFalse();
		softly.assertAll();
	}

}
