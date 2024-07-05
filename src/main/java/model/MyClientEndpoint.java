package model;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JTextArea;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint
 public  class MyClientEndpoint {
	
	private Session session = null;
	private JTextArea textAreaMessage;

	public MyClientEndpoint(JTextArea textAreaMessage) throws URISyntaxException, DeploymentException, IOException {
		URI uri = new URI("ws://localhost:8080/WebSocketChatServer/chatRoomServer");
		ContainerProvider.getWebSocketContainer().connectToServer(this, uri);
		this.textAreaMessage = textAreaMessage;
	}
	
	@OnOpen
	public void handleOpen(Session session) {
		this.session = session;
		System.out.println("Connected to Server!");
	}

	@OnMessage
	public void handleMessage(String message) {
		System.out.println("Response from Server: " + message);
		textAreaMessage.append(message + "\n");
	}

	@OnClose
	public void handleClose() {
		System.out.println("Disconnected to Server!");
	}

	@OnError
	public void handleError(Throwable t) {
		t.printStackTrace();
	}
	
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}
	
	public void disconnect() throws IOException {
		this.session.close();
	}
	
}

