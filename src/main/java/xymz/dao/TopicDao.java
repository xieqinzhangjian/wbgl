package xymz.dao;

import java.util.List;

import xymz.pojo.KnowledgeMenu;
import xymz.pojo.Topic;

public interface TopicDao extends BaseDao<Topic> {

	public List<Topic> queryByKnowledgeMenu(KnowledgeMenu kMenu);
	public List<Topic> queryAllBylastUpdateTime();
	public Topic queryByTitle(String title);
	public List<Topic> getPageBean(Topic topic);
	
}
