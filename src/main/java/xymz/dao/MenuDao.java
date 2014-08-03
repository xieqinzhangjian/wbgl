package xymz.dao;

import java.util.List;

import xymz.pojo.Menu;

public interface MenuDao extends BaseDao<Menu> {

	public List<Menu> getAllParent(String hql);
	
	public void delMenu(String hql);
}
