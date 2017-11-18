package com.authenteq.ws;

import java.net.URI;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import com.authenteq.util.ScannerUtil;


/**
 * The Class BigchainDbWSSessionManager.
 */
@ClientEndpoint
public class BigchainDbWSSessionManager {
	
	private final static Logger LOGGER = Logger.getLogger(BigchainDbWSSessionManager.class.getName());

	/** The user session. */
	Session userSession = null;
	
	/** The message handler. */
	private MessageHandler messageHandler;

	/**
	 * Instantiates a new bigchain db WS session manager.
	 *
	 * @param endpointURI the endpoint URI
	 * @param messageHandler the message handler
	 */
	public BigchainDbWSSessionManager(URI endpointURI, MessageHandler messageHandler) {
		try {

			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			container.setDefaultMaxSessionIdleTimeout(-1);
			this.messageHandler = messageHandler;
			container.connectToServer(this, endpointURI);
			ScannerUtil.monitorExit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Callback hook for Connection open events.
	 *
	 * @param userSession
	 *            the userSession which is opened.
	 */
	@OnOpen
	public void onOpen(Session userSession) {
		LOGGER.info("Opening Websocket");
		this.userSession = userSession;
	}

	/**
	 * Callback hook for Connection close events.
	 *
	 * @param userSession
	 *            the userSession which is getting closed.
	 * @param reason
	 *            the reason for connection close
	 */
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		LOGGER.info("Closing Websocket");
		this.userSession = null;
	}

	/**
	 * Callback hook for Message Events. This method will be invoked when a
	 * client send a message.
	 *
	 * @param message
	 *            The text message
	 */
	@OnMessage
	public void onMessage(String message) {
		LOGGER.info(message);
		if (this.messageHandler != null) {
			this.messageHandler.handleMessage(message);
		}
	}

	/**
	 * register message handler.
	 *
	 * @param msgHandler the msg handler
	 */
	public void addMessageHandler(MessageHandler msgHandler) {
		this.messageHandler = msgHandler;
	}

	/**
	 * Send a message.
	 *
	 * @param message the message
	 */
	public void sendMessage(String message) {
		this.userSession.getAsyncRemote().sendText(message);
	}

}
