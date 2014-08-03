package xymz.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import xymz.pojo.KnowledgeMenu;

@Controller
@Scope("prototype")
public class KnowledgeMenuAction extends BaseAction<KnowledgeMenu> {

	public void queryTree(){
		
		try {
			writeJson(knowledgeMenuService.queryTree());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
