package com.bbytes.zorba;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bbytes.zorba.domain.Priority;
import com.bbytes.zorba.domain.ZorbaRequest;
import com.bbytes.zorba.exception.ClientException;

/**
 * Test class for {@link RabbitMQSender}
 * 
 * @author Dhanush Gopinath
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/zorba-client-test-context.xml" })
public class ZorbaClientTest {

	@Autowired
	private ZorbaClient zorbaClient;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testClient() throws ClientException {
		ZorbaRequest zorbaRequest = new ZorbaRequest();
		zorbaRequest.setId("testid");
		zorbaClient.send(zorbaRequest, Priority.HIGH);
	}


}
