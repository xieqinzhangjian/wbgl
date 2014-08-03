package xymz.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import xymz.model.UserSimple;
import xymz.pojo.DataGrid;
import xymz.pojo.Json;
import xymz.pojo.User;

@Controller("userAction")
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	public void save() {
		Json j = userService.save(model);
		try {
			writeJson(j);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void login() {
		Json j = userService.login(model);
		try {
			if(j.getObj()!=null){
				User u= (User) j.getObj();
				session.put("user",u);
			}
			writeJson(j);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void dataGrid(){
		DataGrid dataGrid = userService.dataGrid(model);
		try {
			writeJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(){
		Json j = userService.delete(model);

			try {
				writeJson(j);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	public void edit(){
		Json j  = userService.update(model);
		try {
			writeJson(j);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void list(){
		try {
			writeJson(userService.query());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void logout(){
		User u = (User) session.get("user");
		session.remove("user");
		try {
			writeJson(u);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void editPassword(){
		
		try {
			writeJson(userService.editPassword(model.getId(), model.getPassword()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
