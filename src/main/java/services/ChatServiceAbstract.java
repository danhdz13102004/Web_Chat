package services;


import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import model.dto.FileDTO;
import model.dto.MessageDTO;
import socket.ChatWebSocket;



public abstract class ChatServiceAbstract {

//	protected static final Set<ChatWebSocket> chatWebsockets = new CopyOnWriteArraySet<>();
	
	protected static final ConcurrentHashMap<String, ChatWebSocket> chatWebsockets = new ConcurrentHashMap<>();
	
	public abstract boolean register(ChatWebSocket chatWebsocket);

	public abstract boolean close(ChatWebSocket chatWebsocket);

	public abstract void sendMessageToAllUsers(MessageDTO message);

	public abstract void sendMessageToOneUser(MessageDTO message);

	public abstract void handleFileUpload(ByteBuffer byteBuffer, boolean last, Queue<FileDTO> fileDTOs);

	public abstract boolean isUserOnline(String username);
	
	public abstract void handleCreateGroup(MessageDTO messageDTO);
	
	public abstract void handleAddMember(MessageDTO messageDTO);
	
	public abstract void handleAcceptRequest(MessageDTO messageDTO);
	
	protected Set<String> getUsernames() {
		Set<String> usernames = new HashSet<String>();
//		chatWebsockets.forEach(chatWebsocket -> {
//			usernames.add(chatWebsocket.getUsername());
//		});
		return usernames;
	}
}