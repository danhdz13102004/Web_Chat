package mapper.impl;

import java.sql.ResultSet;

import mapper.InterfaceMapper;
import model.Message;

public class MessageMapper implements InterfaceMapper<Message> {

	@Override
	public Message mapRow(ResultSet rs) {
		try {
			Message message = new Message();
			message.setId(rs.getString("id"));
			message.setSender(rs.getString("sender"));
			message.setReceiver(rs.getString("receiver"));
			message.setAvatar(rs.getString("avatar"));
			message.setContent(rs.getString("content"));
			message.setTime(rs.getTimestamp("time"));
			message.setType(rs.getString("type"));
			message.setGroupId(rs.getString("groupid"));
			message.setStatus(rs.getBoolean("status"));
			return message;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
}
