package xymz.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import xymz.pojo.Classify;

@Controller("classifyAction")
@Scope("prototype")
public class ClassifyAction extends BaseAction<Classify> {

	public void list(){
		List<Classify> clist = classifyService.queryAllparent();
		try {
			writeJson(clist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void edit(){
		try {
			writeJson(classifyService.update(model));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void save(){
		System.out.println("好像还阿弥知=============");
		
		try {
			writeJson(classifyService.save(model));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void delete(){
		System.out.println("好像还阿弥知=============");
		
		try {
			writeJson(classifyService.delete(model.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	


}
