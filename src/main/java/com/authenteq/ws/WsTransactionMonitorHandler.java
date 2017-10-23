package com.authenteq.ws;

import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

public class WsTransactionMonitorHandler implements StompFrameHandler {

	@Override
	public Type getPayloadType(StompHeaders headers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		System.out.println(payload.toString());
	}

}
