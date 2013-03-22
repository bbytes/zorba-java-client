package com.bbytes.zorba;

import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath*:/spring/zorba-client-test-context.xml");
		ZorbaClient zorbaClient = applicationContext.getBean(ZorbaClient.class);
		AsyncZorbaRequest zorbaRequest = new AsyncZorbaRequest();
		zorbaRequest.setId("111111111");
		zorbaRequest.setTimeOut(5);
		Future<Boolean> responseRecieved1 = null;
		Future<Boolean> responseRecieved2 = null;
		try {
			responseRecieved1 = zorbaClient.sendAsync(zorbaRequest, Priority.HIGH,
					new ZorbaAsyncResponseCallBackHandler() {

						@Override
						public void onResponse(AsyncZorbaResponse asyncZorbaResponse) {
							System.out.println(asyncZorbaResponse.getCorrelationId());
						}

						@Override
						public void onRequestTimeoOut() {
							System.out.println("Request timed out 1");

						}
					});
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		AsyncZorbaRequest zorbaRequest2 = new AsyncZorbaRequest();
		zorbaRequest2.setId("222222222222222");
		zorbaRequest2.setTimeOut(1);
		try {
			responseRecieved2 = zorbaClient.sendAsync(zorbaRequest2, Priority.HIGH,
					new ZorbaAsyncResponseCallBackHandler() {

						@Override
						public void onResponse(AsyncZorbaResponse asyncZorbaResponse) {
							System.out.println(asyncZorbaResponse.getCorrelationId());
						}

						@Override
						public void onRequestTimeoOut() {
							System.out.println("Request timed out 2");

						}
					});
			Boolean done1 = responseRecieved1.get();
			Boolean done2 = responseRecieved2.get();
			zorbaClient.shutdown();
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
}
