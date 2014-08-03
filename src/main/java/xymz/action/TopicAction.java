package xymz.action;

import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import xymz.pojo.KnowledgeMenu;
import xymz.pojo.Topic;
import xymz.pojo.User;
import xymz.service.TopicService;

@Controller
@Scope("prototype")
public class TopicAction extends BaseAction<Topic> {
	public String saveUI() {

		
		return "saveUI";
	}

	public void save() {
		try {
			User u = (User) session.get("user");
			model.setAuthor(u);
			model.setAuthorName(u.getName());
			model.setIpAddr(ServletActionContext. getRequest().getRemoteAddr()); 
			Topic t=  topicService.save(model);
			session.put("topic", t);
			writeJson(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	//	return "topic_show";
		
	}

	public String topicList() {
		List<Topic> topicList = topicService.queryByKnowledgeMenu(model);
		request.put("topicList", topicList);
		request.put("knowledgeMenuId", model.getKnowledgeMenuId());
		return "topicList";
	}
	
	
	
	public String toListUI(){
		
		//菜单
		Set<KnowledgeMenu> menuSet =knowledgeMenuService.queryTree();
		session.put("menuSet", menuSet);
		
		//主题列表
		request.put("pageBean", topicService.getPageBean(model));
		request.put("conditionStr", model.getConditionStr());
		request.put("conditionType", model.getConditionType());
		request.put("knowledgeMenuId", model.getKnowledgeMenuId());

		List<Topic> topicList = topicService.query();
		request.put("topicList", topicList);
		
		return "toListUI";
	}
	
	
	
	public String condition(){
		
		//菜单
		Set<KnowledgeMenu> menuSet =knowledgeMenuService.queryTree();
		session.put("menuSet", menuSet);
		
		//主题列表
		request.put("pageBean", topicService.getPageBean(model));
		request.put("conditionStr", model.getConditionStr());
		request.put("conditionType", model.getConditionType());
		request.put("knowledgeMenuId", model.getKnowledgeMenuId());

		List<Topic> topicList = topicService.query();
		request.put("topicList", topicList);
		
		try {
			writeJson(new User());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "toListUI";
	}
	
	
	public String topicShowById(){
		
		Topic topic = topicService.topicShowById(model.getId());
		request.put("topic", topic);
		return "topic_show";
	}
	
	

}
