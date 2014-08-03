package xymz.service;

import java.util.List;
import java.util.Map;

import xymz.model.UserSimple;
import xymz.pojo.DataGrid;
import xymz.pojo.Json;
import xymz.pojo.User;

public interface UserService {
	public Json save(User user);
	public void sayHello();
	public List<UserSimple> query();
	public DataGrid dataGrid(User u);
	public Json delete(User user);
	public Json update(User user);
	public Json login(User user);
	public User get(int id);
	public Json editPassword(int id,String password);
	
}
