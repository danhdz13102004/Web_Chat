package serializer;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import model.Group_Users;

public class GroupUsersSerializer implements JsonSerializer<Group_Users> {

	@Override
	public JsonElement serialize(Group_Users src, Type typeOfSrc, JsonSerializationContext context) {
final JsonObject jsonObject = new JsonObject();
		
		jsonObject.addProperty("id",src.getGroup().getId());
		jsonObject.addProperty("name",src.getGroup().getName());
		jsonObject.addProperty("avatar",src.getGroup().getAvatar());
		
		return jsonObject;
	}

}
