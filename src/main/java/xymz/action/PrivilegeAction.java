package xymz.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import xymz.model.UserSimple;
import xymz.pojo.Json;
import xymz.pojo.Privilege;
import xymz.pojo.User;

@Controller
@Scope("prototype")
public class PrivilegeAction extends BaseAction<Privilege> {

	public  void approve(){
		User user = (User) session.get("user");
		
		Json j = privilegeService.approve(model.getUrl(),user);
		try {
			writeJson(j);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
