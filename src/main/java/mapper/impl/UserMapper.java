package mapper.impl;

import java.sql.ResultSet;

import mapper.InterfaceMapper;
import model.User;

public class UserMapper implements InterfaceMapper<User> {

	@Override
	public User mapRow(ResultSet rs) {
		try {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setAvatar(rs.getString("avatar"));
			user.setFullname(rs.getString("fullname"));
			user.setStatus(rs.getBoolean("status"));
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
}
