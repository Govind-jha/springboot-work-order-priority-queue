package com.aspect.workorder.dao;

import java.util.concurrent.PriorityBlockingQueue;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.aspect.workorder.factory.ServiceRequestFactory;
import com.aspect.workorder.model.servicerequest.ServiceRequest;

@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest
public class ServiceRequestQueueTest {

	@Autowired
	RankedServiceRequestQueue queue;

	private final Long requesterId_mgmt = 30L;
	private final Long timeOfRequest_mgmt = System.currentTimeMillis();

	private final Long requesterId_vip = 50L;
	private final Long timeOfRequest_vip = System.currentTimeMillis();

	private final Long requesterId_normal = 7L;
	private final Long timeOfRequest_normal = System.currentTimeMillis();

	private final Long requesterId_priority = 9L;
	private final Long timeOfRequest_priority = System.currentTimeMillis();

	@Test
	public void testServiceRequestQueue() {
		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(queue).as("Queue is not null.").isNotNull();
		softly.assertThat(queue).as("Queue is a priority blocking queue").isInstanceOf(RankedServiceRequestQueue.class);
		softly.assertAll();
	}

	@Test
	public void testGetPQ() {
		final PriorityBlockingQueue<ServiceRequest> q = queue.getQueue();
		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(q).as("Queue is not null.").isNotNull();
		softly.assertThat(q).as("Queue is a priority blocking queue").isInstanceOf(PriorityBlockingQueue.class);
		softly.assertAll();
	}

	@Test
	public void testGetPQ_priorityIsCorrect() {

		final PriorityBlockingQueue<ServiceRequest> q = new PriorityBlockingQueue<>(queue.getQueue());

		ServiceRequest request1 = ServiceRequestFactory.getRequest(requesterId_mgmt, timeOfRequest_mgmt);
		ServiceRequest request2 = ServiceRequestFactory.getRequest(requesterId_vip, timeOfRequest_vip);
		ServiceRequest request3 = ServiceRequestFactory.getRequest(requesterId_priority, timeOfRequest_priority);
		ServiceRequest request4 = ServiceRequestFactory.getRequest(requesterId_normal, timeOfRequest_normal);

		q.add(request1);
		q.add(request2);
		q.add(request3);
		q.add(request4);

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(q).as("Queue is not null.").isNotNull();
		softly.assertThat(q).as("Queue is a priority blocking queue.").isInstanceOf(PriorityBlockingQueue.class);

		Long prev = Long.MIN_VALUE;
		while (q.peek() != null) {
			Long rank = q.poll().getRank();
			softly.assertThat(rank > prev).as("Queue is sorted in correct priority").isTrue();
		}

		softly.assertAll();
	}

}
