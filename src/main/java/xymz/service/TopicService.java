package xymz.service;

import java.util.List;

import xymz.pojo.PageBean;
import xymz.pojo.Topic;

public interface TopicService {

	
	public Topic save(Topic topic);
	public Topic topicShowById(int id);
	
	public List<Topic> queryByKnowledgeMenu(Topic topic);

	public  List<Topic> query();
	public PageBean getPageBean(Topic topic);
	
	
}
