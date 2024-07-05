package model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class Friend {
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_sender", referencedColumnName = "id")
	private User sender;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_receiver", referencedColumnName = "id")
	private User receiver;
	
	private Status status;
	
	
	public Friend() {
		
	}
	
	
	
	public Friend(Long id, User sender, User receiver, Status status) {
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.status = status;
	}

	public Friend(String sender, String receiver, Status status) {
		User u1 = new User(); u1.setId(sender);
		User u2 = new User(); u2.setId(receiver);
		this.sender = u1;
		this.receiver = u2;
		this.status = status;
	}


	


	public Long getId() {
		return id;
	}







	public void setId(Long id) {
		this.id = id;
	}







	public User getSender() {
		return sender;
	}







	public void setSender(User sender) {
		this.sender = sender;
	}







	public User getReceiver() {
		return receiver;
	}







	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}







	public Status getStatus() {
		return status;
	}







	public void setStatus(Status status) {
		this.status = status;
	}







	public enum Status
    {
    	CHOXACNHAN("Chờ xác nhận"),
    	DAXACNHAN("Đã xác nhận"),
    	DAHUY("Đã hủy");
    	private String message;
    	private Status(String mess) {
			// TODO Auto-generated constructor stub
    		this.message=mess;
    	}
    	public String getMessage()
    	{
    		return this.message;
    	}
    	
    }
}
