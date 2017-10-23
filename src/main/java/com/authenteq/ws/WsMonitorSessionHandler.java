package com.authenteq.ws;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import com.authenteq.model.Globals;

public class WsMonitorSessionHandler implements StompSessionHandler{

	@Override
	public Type getPayloadType(StompHeaders arg0) {
		System.out.println("getPayloadType");
		return null;
	}

	@Override
	public void handleFrame(StompHeaders arg0, Object arg1) {
		System.out.println("handleFrame");

	}

	@Override
	public void afterConnected(StompSession session, StompHeaders arg1) {
		System.out.println(session.isConnected());
		session.subscribe("/valid_transactions", new WsTransactionMonitorHandler());
	}

	@Override
	public void handleException(StompSession arg0, StompCommand arg1, StompHeaders arg2, byte[] arg3, Throwable arg4) {
		System.out.println("Exception");
		arg4.printStackTrace();

	}

	@Override
	public void handleTransportError(StompSession arg0, Throwable arg1) {
		System.out.println("Error");

	}
	

}
