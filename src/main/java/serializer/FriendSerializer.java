package serializer;

import java.lang.reflect.Type;

import org.hibernate.Session;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dao.MessageDAO;
import model.Friend;
import model.Message;
import util.HibernateUtil;

public class FriendSerializer implements JsonSerializer<Friend> {

	@Override
	public JsonElement serialize(Friend src, Type typeOfSrc, JsonSerializationContext context) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id_sender",src.getSender().getId());
		jsonObject.addProperty("name_sender",src.getSender().getFullname());
		jsonObject.addProperty("avatar_sender",src.getSender().getAvatar());
		jsonObject.addProperty("sender_online", String.valueOf(src.getSender().isStatus()));
		jsonObject.addProperty("sender_lastmsg", src.getSender().getLastmessage());
		
		
		jsonObject.addProperty("id_receiver",src.getReceiver().getId());
		jsonObject.addProperty("name_receiver",src.getReceiver().getFullname());
		jsonObject.addProperty("avatar_receiver",src.getReceiver().getAvatar());
		jsonObject.addProperty("receiver_online", String.valueOf(src.getReceiver().isStatus()));
		jsonObject.addProperty("receiver_lastmsg", src.getSender().getLastmessage());
		
		jsonObject.addProperty("status",src.getStatus().getMessage());
		
		return jsonObject;
	}
	

}
