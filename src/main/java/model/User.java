package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user")
public class User {
	@Id
	private String id;
	private String username;
	private String password;
	private String fullname;
	private String avatar;
	private boolean status;
	private boolean satatus_activity;
	@Transient
	public String lastmessage = "Let's start conversation";
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friend> sender_role;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friend> receiver_role;
	
	public User() {
	}
	
	
	



	public User(String username, String password, String fullname, String avatar, boolean status) {
		super();
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.avatar = avatar;
		this.status = status;
	}






	


	public User(String id, String username, String password, String fullname, String avatar, boolean status) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.avatar = avatar;
		this.status = status;
	}
	
	



	public User(String id, String fullname, String avatar) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.avatar = avatar;
	}



	public String getFullname() {
		return fullname;
	}



	public void setFullname(String fullname) {
		this.fullname = fullname;
	}



	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}



	public boolean isSatatus_activity() {
		return satatus_activity;
	}



	public void setSatatus_activity(boolean satatus_activity) {
		this.satatus_activity = satatus_activity;
	}



	public String getLastmessage() {
		return lastmessage;
	}



	public void setLastmessage(String lastmessage) {
		this.lastmessage = lastmessage;
	}



	public List<Friend> getSender_role() {
		return sender_role;
	}



	public void setSender_role(List<Friend> sender_role) {
		this.sender_role = sender_role;
	}



	public List<Friend> getReceiver_role() {
		return receiver_role;
	}



	public void setReceiver_role(List<Friend> receiver_role) {
		this.receiver_role = receiver_role;
	}



	@Override
	public String toString() {
		return "User [id=" + id + ", fullname=" + fullname + ", avatar=" + avatar + ", status=" + status
				+ ", satatus_activity=" + satatus_activity + ", lastmessage=" + lastmessage + "]";
	}
	
	
	
	

	

	
	
}
