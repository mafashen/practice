package com.mafashen.spring.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author mafashen
 * created on 2018/12/2.
 */
public class TestEventListener implements ApplicationListener {

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof TestEvent){
			TestEvent testEvent = (TestEvent) event;
			testEvent.print();
		}
	}
}
