package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ChatGroup {
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private String avatar;
	
	private boolean status;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chatGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Group_Users> group_users;

	public ChatGroup() {
	}

	public ChatGroup(Long id, String name, String avatar, boolean status) {
		super();
		this.id = id;
		this.name = name;
		this.avatar = avatar;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<Group_Users> getGroup_users() {
		return group_users;
	}

	public void setGroup_users(List<Group_Users> group_users) {
		this.group_users = group_users;
	}

	
	
	
}
