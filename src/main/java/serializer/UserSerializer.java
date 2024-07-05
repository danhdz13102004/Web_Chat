package serializer;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import model.User;

public class UserSerializer implements JsonSerializer<User> {

	@Override
	public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {
final JsonObject jsonObject = new JsonObject();
		
		jsonObject.addProperty("id",src.getId());
		jsonObject.addProperty("fullname",src.getFullname());
		jsonObject.addProperty("avatar",src.getAvatar());
		jsonObject.addProperty("isonline", src.isSatatus_activity());
		jsonObject.addProperty("lastmessage", src.getLastmessage());
		return jsonObject;
	}
	
}
