package xymz.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import xymz.pojo.WB;
@Controller("wBAction")
@Scope("prototype")
public class WBAction extends BaseAction<WB> {
	
	public void queryByName() throws IOException{
		try {
			writeJson(wBService.queryByName(model.getName()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
