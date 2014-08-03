package xymz.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import xymz.dao.KnowledgeMenuDao;
import xymz.pojo.KnowledgeMenu;

@Repository("knowledgeMenuDao")
public class KnowledgeMenuDaoImpl extends BaseDaoImpl<KnowledgeMenu> implements KnowledgeMenuDao {

	@Override
	public List<KnowledgeMenu> queryTree() {
		String hql="from KnowledgeMenu k where k.parentMenu is null";
		return getSession().createQuery(hql).list();
	}


	
	
}
