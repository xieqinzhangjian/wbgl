package xymz.dao;

import java.util.List;

import xymz.pojo.KnowledgeMenu;

public interface KnowledgeMenuDao extends BaseDao<KnowledgeMenu>{
	
	public List<KnowledgeMenu> queryTree();
}
