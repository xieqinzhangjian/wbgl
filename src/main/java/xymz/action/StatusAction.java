package xymz.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import xymz.pojo.Status;

@Controller("statusAction")
@Scope("prototype")
public class StatusAction extends BaseAction<Status> {
	
	public void list(){
		try {
			writeJson(statusService.query());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
