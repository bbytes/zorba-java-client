package com.bbytes.zorba;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bbytes.zorba.domain.AsyncZorbaRequest;
import com.bbytes.zorba.domain.AsyncZorbaResponse;
import com.bbytes.zorba.domain.Priority;
import com.bbytes.zorba.exception.ZorbaClientException;
import com.bbytes.zorba.handler.ZorbaAsyncResponseCallBackHandler;

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
	public void testClient() throws ZorbaClientException, InterruptedException {
		AsyncZorbaRequest zorbaRequest = new AsyncZorbaRequest();
		zorbaRequest.setId("222222222222222");
		zorbaRequest.setTimeOut(15);
		zorbaClient.sendAsync(zorbaRequest, Priority.HIGH, new ZorbaAsyncResponseCallBackHandler() {

			@Override
			public void onResponse(AsyncZorbaResponse asyncZorbaResponse) {
				System.out.println(asyncZorbaResponse.getCorrelationId());

			}

			@Override
			public void onRequestTimeoOut() {
				System.out.println("Request timed out");
				
			}
		});
	
	}

	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:/spring/zorba-client-test-context.xml");
		ZorbaClient zorbaClient = applicationContext.getBean(ZorbaClient.class);
		AsyncZorbaRequest zorbaRequest = new AsyncZorbaRequest();
		zorbaRequest.setId("222222222222222");
		zorbaRequest.setTimeOut(15);
		try {
			zorbaClient.sendAsync(zorbaRequest, Priority.HIGH, new ZorbaAsyncResponseCallBackHandler() {

				@Override
				public void onResponse(AsyncZorbaResponse asyncZorbaResponse) {
					System.out.println(asyncZorbaResponse.getCorrelationId());

				}

				@Override
				public void onRequestTimeoOut() {
					System.out.println("Request timed out");
					
				}
			});
		} catch (ZorbaClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
