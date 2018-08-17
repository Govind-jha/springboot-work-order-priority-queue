package com.aspect.workorder.utility;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.aspect.workorder.factory.ServiceRequestFactory;
import com.aspect.workorder.model.servicerequest.ServiceRequest;
import com.aspect.workorder.utility.RankingComparator;

@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest
public class RankingComparatorTest {

	@Autowired
	RankingComparator comparator;

	private final Long requesterId_Management1 = 30L;
	private final Long timeOfRequest_Management1 = 300000000L;

	private final Long requesterId_Management2 = 60L;
	private final Long timeOfRequest_Management2 = 600000000L;

	private final Long requesterId_Priority = 9L;
	private final Long timeOfRequest_Priority = 605000000L;

	private final Long requesterId_Normal = 23L;
	private final Long timeOfRequest_Normal = 900000000L;

	@Test(expected = NullPointerException.class)
	public void testCompare_bothNull() {
		assert comparator.compare(null, null) != 0;
	}

	@Test(expected = NullPointerException.class)
	public void testCompare_secondNullAndManagement() {
		final ServiceRequest req_mgt1 = ServiceRequestFactory.getRequest(requesterId_Management1,
				timeOfRequest_Management1);
		assert comparator.compare(req_mgt1, null) != 0;
	}

	@Test(expected = NullPointerException.class)
	public void testCompare_firstNullAndManagement() {
		final ServiceRequest req_mgt1 = ServiceRequestFactory.getRequest(requesterId_Management1,
				timeOfRequest_Management1);
		assert comparator.compare(null, req_mgt1) != 0;
	}

	@Test(expected = NullPointerException.class)
	public void testCompare_firstNullAndPriority() {
		final ServiceRequest req_priority = ServiceRequestFactory.getRequest(requesterId_Priority,
				timeOfRequest_Priority);
		assert comparator.compare(req_priority, null) != 0;
	}

	@Test(expected = NullPointerException.class)
	public void testCompare_secondNullAndPriority() {
		final ServiceRequest req_priority = ServiceRequestFactory.getRequest(requesterId_Priority,
				timeOfRequest_Priority);
		assert comparator.compare(null, req_priority) != 0;
	}

	@Test
	public void testCompare_bothManagement() {
		final ServiceRequest req_mgt1 = ServiceRequestFactory.getRequest(requesterId_Management1,
				timeOfRequest_Management1);
		final ServiceRequest req_mgt2 = ServiceRequestFactory.getRequest(requesterId_Management2,
				timeOfRequest_Management2);

		final int result = comparator.compare(req_mgt1, req_mgt2);
		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result).as("result is an integer").isLessThan(0);
		softly.assertAll();
	}

	@Test
	public void testCompare_managementVsPriority() {
		final ServiceRequest req_mgt1 = ServiceRequestFactory.getRequest(requesterId_Management1,
				timeOfRequest_Management1);
		final ServiceRequest req_priority = ServiceRequestFactory.getRequest(requesterId_Priority,
				timeOfRequest_Priority);

		final int result = comparator.compare(req_mgt1, req_priority);
		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result).as("result is an integer").isEqualTo(-1);
		softly.assertAll();
	}

	@Test
	public void testCompare_priorityVsManagement() {
		final ServiceRequest req_mgt1 = ServiceRequestFactory.getRequest(requesterId_Management1,
				timeOfRequest_Management1);
		final ServiceRequest req_priority = ServiceRequestFactory.getRequest(requesterId_Priority,
				timeOfRequest_Priority);

		final int result = comparator.compare(req_priority, req_mgt1);
		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result).as("result is an integer").isEqualTo(1);
		softly.assertAll();
	}

	@Test
	public void testCompare_priorityVsNormal() {
		final ServiceRequest req_normal = ServiceRequestFactory.getRequest(requesterId_Normal, timeOfRequest_Normal);
		final ServiceRequest req_priority = ServiceRequestFactory.getRequest(requesterId_Priority,
				timeOfRequest_Priority);

		final int result = comparator.compare(req_priority, req_normal);
		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result).as("result is an integer").isGreaterThan(1);
		softly.assertAll();
	}

}
