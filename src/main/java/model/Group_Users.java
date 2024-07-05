package model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Group_Users {
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Id_group", referencedColumnName = "id")
	private ChatGroup chatGroup;
	
	private String id_user;
	
	private boolean status;
	
	private Role role;
	
	public static enum Role
	{
		ME("MEMBER"),
		AD("ADMIN");
		private String roleName;
		private Role(String rname)
		{
			this.roleName=rname;
		}
		public String getRoleName()
		{
			return this.roleName;
		}
	}

	public Group_Users() {
	}

	

	


	public Group_Users(Long id, ChatGroup chatGroup, String id_user, boolean status, Role role) {
		super();
		this.id = id;
		this.chatGroup = chatGroup;
		this.id_user = id_user;
		this.status = status;
		this.role = role;
	}



	public String getId_user() {
		return id_user;
	}

	public void setId_user(String id_user) {
		this.id_user = id_user;
	}






	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ChatGroup getGroup() {
		return chatGroup;
	}

	public void setGroup(ChatGroup chatGroup) {
		this.chatGroup = chatGroup;
	}

	
	

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
}
