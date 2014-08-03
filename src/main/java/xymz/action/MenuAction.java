package xymz.action;

import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import xymz.pojo.Menu;
import xymz.pojo.User;

@Controller("menuAction")
@Scope("prototype")
public class MenuAction extends BaseAction<Menu> {

	public void allparent() {
		User user = (User)session.get("user");
		Set<Menu> menus=null;
		if(user!=null){
		  menus = menuService.AllParent(user);
			
		}
		if (menus!=null) {
			try {
				writeJson(menus);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void save() {
		menuService.save(model);
	}
}
