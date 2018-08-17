package com.aspect.workorder.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.aspect.workorder.dao.RankedServiceRequestQueueDAOImpl;
import com.aspect.workorder.factory.ServiceRequestFactory;
import com.aspect.workorder.model.servicerequest.ServiceRequest;

@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest
public class ServiceRequestDAOTest {

	@Autowired
	RankedServiceRequestQueueDAOImpl dao;

	private final Long requesterId_1 = 30L;
	private final Long timeOfRequest_1 = 300L;

	private final Long requesterId_2 = 50L;
	private final Long timeOfRequest_2 = 500L;

	private final Long requesterId_3 = 7L;
	private final Long timeOfRequest_3 = 700L;

	private final Long requesterId_4 = 9L;

	@Test
	public void testServiceRequestDAO() {
		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(dao).as("DAO is not null").isNotNull();
		softly.assertThat(dao).as("dao is instance of ServiceRequestDAO").isInstanceOf(RankedServiceRequestQueueDAOImpl.class);
		softly.assertThat(dao.getServiceRequestByIdMap()).as("dao is successfully initialized").isInstanceOf(Map.class);
		softly.assertAll();
	}

	@Test
	public void testEnque() {
		final Boolean result = dao.enque(ServiceRequestFactory.getRequest(requesterId_1, timeOfRequest_1));

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result).as("Enqueue was sucessful.").isTrue();
		softly.assertAll();
	}

	@Test
	public void testDeque_whenQueueIsEmpty() {
		dao.getQueue().stream().forEach(e -> dao.remove(e.getRequesterId()));

		Optional<ServiceRequest> result = dao.deque();

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result.isPresent()).as("result is not present as the queue is empty.").isFalse();
		softly.assertAll();

	}

	@Test
	public void testDeque_whenQueueIsNotEmpty() {
		dao.enque(ServiceRequestFactory.getRequest(requesterId_2, timeOfRequest_2));
		final Optional<ServiceRequest> result = dao.deque();

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result.isPresent()).as("result is present as the queue is not empty.").isTrue();
		softly.assertThat(result).as("Result is of type Optional").isInstanceOf(Optional.class);
		softly.assertThat(result.get()).as("Result contains ServiceRequest").isInstanceOf(ServiceRequest.class);
		softly.assertThat(result.get().getRequesterId()).as("Service request has a valid id").isInstanceOf(Long.class);
		softly.assertAll();
	}

	@Test
	public void testRemove() {
		dao.enque(ServiceRequestFactory.getRequest(requesterId_3, timeOfRequest_3));
		final Boolean result = dao.remove(requesterId_3);

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result).as("Requester id was removed from the queue").isTrue();
		softly.assertAll();

	}

	@Test
	public void testRemove_requestNotPresentInQueue() {
		Boolean result = dao.remove(requesterId_4);

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result).as("Requester id was not removed from the queue").isFalse();
		softly.assertAll();

	}

	@Test
	public void testGetQueue() {
		final ServiceRequest request = ServiceRequestFactory.getRequest(requesterId_1, timeOfRequest_1);
		dao.enque(request);
		final List<ServiceRequest> queue = dao.getQueue();

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(queue).as("This is a list.").isInstanceOf(List.class);
		softly.assertThat(queue.size()).as("List is not empty").isGreaterThan(0);
		softly.assertThat(queue.get(0)).as("List contains Service Request").isInstanceOf(ServiceRequest.class);
		softly.assertThat(queue.contains(request))
				.as("List contains request from requester with id requesterid_1").isTrue();
		softly.assertAll();

	}

	@Test
	public void testGetServiceRequestByIdMap() {
		final Map<String, ServiceRequest> result = dao.getServiceRequestByIdMap();
		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result).as("result is not null").isNotNull();
		softly.assertThat(result).as("result is instance of Map").isInstanceOf(Map.class);
		softly.assertAll();
	}

}
