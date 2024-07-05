package mapper;

import java.sql.ResultSet;

public interface InterfaceMapper<T> {
	public T mapRow(ResultSet rs);
}
