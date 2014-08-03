package xymz.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import xymz.pojo.Json;
import xymz.pojo.SellMovie;
import xymz.pojo.User;

@Controller("sellMovieAction")
@Scope("prototype")
public class SellMovieAction extends BaseAction<SellMovie>{

	
	public void save(){
		try {
		User user =	(User) session.get("user");
			writeJson(sellMovieService.save(model,	user.getName()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void dataGrid(){
		
		try {
			writeJson(sellMovieService.dataGrid(model));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void xuFei(){
		User user = (User) session.get("user");
		try {
			writeJson(	sellMovieService.updateXuFei(user, model));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void xuFeiLogBySellMovie(){
		try {
			writeJson(sellMovieService.xuFeiLogBySellMovie(model));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void delete(){
		
		try {
			Json j = sellMovieService.delete(model.getId());
			writeJson(j);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	//前台js自动判断是否到期,如果到期,自动修改过期
	public void due(){
		sellMovieService.updateDue(model.getId());
	}
	
	
	public void edit(){
		try {
			Json j = sellMovieService.update(model);
			writeJson(j);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
	}

	
}
