package model.dto;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageDTO {

	@JsonProperty("sender")
	private String sender;
	@JsonProperty("avatar")
	private String avatar;
	@JsonProperty("message")
	private String message;
	@JsonProperty("type")
	private String type;
	@JsonProperty("receiver")
	private String receiver;
	@JsonProperty("groupId")
	private Long groupId;
	@JsonProperty("onlineList")
	private Set<String> onlineList = new HashSet<String>();
	@JsonProperty("time")
	private Timestamp time;
	@JsonProperty("namefile")
	private String namefile;
	@JsonProperty("listId")
	private ArrayList<String> listId;

	public MessageDTO() {
	}

	@JsonCreator
	public MessageDTO(@JsonProperty("sender") String sender, @JsonProperty("message") String message,
			@JsonProperty("type") String type, @JsonProperty("receiver") String receiver,
			@JsonProperty("groupId") Long groupId,@JsonProperty("avatar") String avatar,@JsonProperty("namefile") String namefile,
			@JsonProperty("listId") ArrayList<String> listId) {
		this.sender = sender;
		this.message = message;
		this.type = type;
		this.receiver = receiver;
		this.groupId = groupId;
		this.avatar = avatar;
		time = Timestamp.valueOf(LocalDateTime.now());
		this.namefile = namefile;
		this.listId = listId;
	}
	
	

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public Set<String> getOnlineList() {
		return onlineList;
	}

	public void setOnlineList(Set<String> onlineList) {
		this.onlineList = onlineList;
	}

	public String getNamefile() {
		return namefile;
	}

	public void setNamefile(String namefile) {
		this.namefile = namefile;
	}

	public ArrayList<String> getListId() {
		return listId;
	}

	public void setListId(ArrayList<String> listId) {
		this.listId = listId;
	}

	
	
	
	
}