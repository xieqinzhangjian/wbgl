package xymz.service.impl;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import xymz.dao.KnowledgeMenuDao;
import xymz.pojo.KnowledgeMenu;
import xymz.service.KnowledgeMenuService;

@Service("knowledgeMenuService")
public class KnowledgeMenuServiceImpl implements KnowledgeMenuService {
	
	@Resource
	private KnowledgeMenuDao knowledgeMenuDao;
	
	@Override
	public Set<KnowledgeMenu> queryTree() {
	List<KnowledgeMenu>	 knowledgeMenuList = knowledgeMenuDao.queryTree();
	
	Set<KnowledgeMenu> knowledgeMenuSet=new TreeSet<KnowledgeMenu>();
	
	knowledgeMenuSet.addAll(knowledgeMenuList);
	
		return knowledgeMenuSet;
	}

}
