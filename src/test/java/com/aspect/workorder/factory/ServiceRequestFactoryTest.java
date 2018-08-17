package com.aspect.workorder.factory;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.aspect.workorder.factory.ServiceRequestFactory;
import com.aspect.workorder.model.servicerequest.ManagementServiceRequest;
import com.aspect.workorder.model.servicerequest.NormalServiceRequest;
import com.aspect.workorder.model.servicerequest.PriorityServiceRequest;
import com.aspect.workorder.model.servicerequest.ServiceRequest;
import com.aspect.workorder.model.servicerequest.VIPServiceRequest;

@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest
public class ServiceRequestFactoryTest {

	private final Long requesterId_Management = 30L;
	private final Long timeOfRequest_Management = 300L;

	private final Long requesterId_VIP = 50L;
	private final Long timeOfRequest_VIP = 500L;

	private final Long requesterId_Priority = 9L;
	private final Long timeOfRequest_Priority = 700L;

	private final Long requesterId_Normal = 23L;
	private final Long timeOfRequest_Normal = 900L;

	@Test
	public void testGetRequest_Normal() {
		ServiceRequest request = ServiceRequestFactory.getRequest(requesterId_Normal, timeOfRequest_Normal);

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(request).as("Request is of type : Normal").isExactlyInstanceOf(NormalServiceRequest.class);
		softly.assertAll();
	}

	@Test
	public void testGetRequest_Priority() {
		ServiceRequest request = ServiceRequestFactory.getRequest(requesterId_Priority, timeOfRequest_Priority);

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(request).as("Request is of type : Priority").isExactlyInstanceOf(PriorityServiceRequest.class);
		softly.assertAll();
	}

	@Test
	public void testGetRequest_VIP() {
		ServiceRequest request = ServiceRequestFactory.getRequest(requesterId_VIP, timeOfRequest_VIP);

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(request).as("Request is of type : VIP").isExactlyInstanceOf(VIPServiceRequest.class);
		softly.assertAll();
	}

	@Test
	public void testGetRequest_Management() {
		ServiceRequest request = ServiceRequestFactory.getRequest(requesterId_Management, timeOfRequest_Management);

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(request).as("Request is of type : NORMAL").isExactlyInstanceOf(ManagementServiceRequest.class);
		softly.assertAll();
	}

}
