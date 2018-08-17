package com.aspect.workorder.resources;

import java.io.IOException;
import java.util.List;

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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WorkOrderResourceV1_getPositionTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	ObjectMapper mapper;

	/*****************************************************************************
	 * The below values are used to create request for OrderGift API, and are
	 * inserted in the order as its declaration.
	 *****************************************************************************/
	private final Long[] requesterIds = { 30L, 50L, 9L };
	private final Long[] timeOfRequest = { 300L, 500L, 900L };
	private final Long requesterId = 19L;
	private final Long firstElement = requesterIds[0]; // it's management ID
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
	public void testGetPosition_withIdNotPresentInQueue() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<WorkOrderResponse> responseEntity = restTemplate.getForEntity(
				AppConstant.RESOURCE_PATH_WORK_ORDER
						+ AppConstant.WORK_ORDER_OPR_GET_POSITION.replaceFirst("\\{.*\\}", requesterId.toString()),
				WorkOrderResponse.class);
		
		List<String> response = responseEntity.getHeaders().get(AppConstant.RESPONSE_HEADER_APP_DIAGNOSTIC);
		
		assert response != null;
		
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(responseEntity.getStatusCodeValue()).as("Request executed successfully with status code 204.")
				.isEqualTo(204);
		softly.assertThat(response).as("Response for failure to find the is in the queue.")
				.contains(new String[]{String.format(AppConstant.MSG_NO_REQUEST_WAS_FOUND_FOR_ID, requesterId)});
		softly.assertAll();
	}

	@Test
	public void testGetPosition_withIdPresentInQueue() {

		ResponseEntity<WorkOrderResponse> responseEntity = restTemplate.getForEntity(
				AppConstant.RESOURCE_PATH_WORK_ORDER
						+ AppConstant.WORK_ORDER_OPR_GET_POSITION.replaceFirst("\\{.*\\}", firstElement.toString()),
				WorkOrderResponse.class);
		WorkOrderResponse response = responseEntity.getBody();

		assert response != null;

		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(responseEntity.getStatusCodeValue()).as("Status code is 200.").isEqualTo(200);
		softly.assertThat(response.getPosition()).as("Position is equal to 1for management id.").isEqualTo(1);
		softly.assertThat(response.getSuccess()).as("Success is true").isTrue();
		softly.assertAll();
	}

}
