package xymz.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import xymz.dao.KnowledgeMenuDao;
import xymz.dao.TopicDao;
import xymz.pojo.KnowledgeMenu;
import xymz.pojo.PageBean;
import xymz.pojo.Topic;
import xymz.service.TopicService;

@Service("topicService")
public class TopicServiceImpl implements TopicService {

	@Resource
	private TopicDao topicDao;

	@Resource
	private KnowledgeMenuDao knowledgeMenuDao;

	@Override
	public Topic save(Topic topic) {
		topic.setPostTime(new Date());
		topic.setLastUpdateTime(new Date());
		topic.setType(Topic.TYPE_NORMAL);
		KnowledgeMenu knowledgeMenu = knowledgeMenuDao.get(topic.getKnowledgeMenuId());
		knowledgeMenu.setTopicTotal(knowledgeMenu.getTopicTotal()+1);
		topic.setkMenu(knowledgeMenu);
	
		topicDao.save(topic);
		return queryByTitle(topic.getTitle());
	}

	public List<Topic> queryByKnowledgeMenu(Topic topic) {
		List<Topic> topicList = topicDao.queryByKnowledgeMenu(knowledgeMenuDao.get(topic.getKnowledgeMenuId()));
		return topicList;
	}

	public Topic queryByTitle(String title) {

		return topicDao.queryByTitle(title);
	}

	@Override
	public List<Topic> query() {
		return topicDao.query();
	}
	
	public PageBean getPageBean(Topic topic) {
		String fromClause = "from Topic t ";
		String whereClause = "";
		String orderByClause = " order by t.lastUpdateTime desc";
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		if(topic.getTitle()!=null && !"".equals(topic.getTitle().trim())){
			whereClause+=" where t.title like:title";
			parameters.put("title", topic.getTitle());
		}
		
		if(topic.getAuthorName()!=null && !"".equals(topic.getAuthorName().trim())){
			if(""==whereClause){
				whereClause+=" where t.authorName =:authorName ";
			}else{
				whereClause+=" and t.authorName =:authorName ";
			}
			parameters.put("authorName", topic.getAuthorName());
		}
		
		
		//条件查询
		if(topic.getConditionStr()!=null && !"".equals(topic.getConditionStr().trim())){
			if(topic.getConditionType()==1){
				if(""==whereClause){
					whereClause+=" where t.authorName =:authorName ";
				}else{
					whereClause+=" and t.authorName =:authorName ";
				}
				parameters.put("authorName", topic.getConditionStr());
			}else{
				if(""==whereClause){
					whereClause+=" where t.title like:title ";
				}else{
					whereClause+=" and t.title like:title ";
				}
				parameters.put("title","%%"+ topic.getConditionStr()+"%%");	
				
			}

		}
		
		
		//按照分类
		if(topic.getKnowledgeMenuId()!=null && topic.getKnowledgeMenuId()!=0){
			if(""==whereClause){
				whereClause+=" where t.kMenu =:kMenu ";
			}else{
				whereClause+=" and t.kMenu =:kMenu ";
			}
			parameters.put("kMenu", knowledgeMenuDao.get(topic.getKnowledgeMenuId()));
			
		}
		
		String hql=fromClause+whereClause+orderByClause;
		
		List<Topic> topicList =topicDao.find(hql, parameters, topic.getCurrentPage(), topic.getPageSize());
		
		Long count = topicDao.count("select count(*) "+hql, parameters);
		
		int newCount = count.intValue();
		
		PageBean pageBean= new PageBean(topic.getCurrentPage(),topic.getPageSize(),topicList,newCount);
		
		return pageBean;
	}

	@Override
	public Topic topicShowById(int id) {
		
		return topicDao.get(id);
	}
	

}
