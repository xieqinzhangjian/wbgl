package xymz.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import xymz.dao.TopicDao;
import xymz.pojo.KnowledgeMenu;
import xymz.pojo.Topic;

@Repository("topicDao")
public class TopicDaoImpl extends BaseDaoImpl<Topic> implements TopicDao {

	@Override
	public List<Topic> queryByKnowledgeMenu(KnowledgeMenu kMenu) {
		String hql="from Topic t where t.kMenu=:kMenu";

		return getSession().createQuery(hql).setParameter("kMenu", kMenu).list();
	}

	@Override
	public List<Topic> queryAllBylastUpdateTime() {
		String hql="from Topic t order by  t.lastUpdateTime desc";
		return getSession().createQuery(hql).list();
	}

	@Override
	public Topic queryByTitle(String title) {
		String hql="from Topic t where t.title=:title";
		return (Topic) getSession().createQuery(hql).setParameter("title", title).uniqueResult();
	}

	@Override
	public List<Topic> getPageBean(Topic topic) {
		String hql="from Topic t where t.";
		
		return null;
	}


}
