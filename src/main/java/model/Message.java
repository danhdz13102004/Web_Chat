package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name =  "message")
public class Message {
	@Id
	private String id;
	private String sender;
	private String receiver;
	private String avatar;
	private String type;
	private String groupId;
	private boolean status;
	private Timestamp time;
	private String content;
	public String name;
	@Transient
	public String lastmessage = "";
	public Message() {

	}
	
	
	
	
	public Message(String id, String sender, String receiver, String avatar, String type, String groupId,
			boolean status, Timestamp time, String content) {
		super();
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.avatar = avatar;
		this.type = type;
		this.groupId = groupId;
		this.status = status;
		this.time = time;
		this.content = content;
	}




	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}




	public String getLastmessage() {
		return lastmessage;
	}




	public void setLastmessage(String lastmessage) {
		this.lastmessage = lastmessage;
	}
	
	
	
	
}
