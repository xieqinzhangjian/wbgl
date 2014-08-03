package xymz.dao.impl;

import java.util.List;

import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import xymz.dao.MenuDao;
import xymz.pojo.Menu;

@SuppressWarnings("unchecked")
@Repository("menuDao")
public class MenuDaoImpl  extends BaseDaoImpl<Menu>implements MenuDao {

	//查询所有父类菜单
	@Override
	public List<Menu> getAllParent(String hql) {
		return getSession().createQuery(hql).list();
	}

	@Override
	public void delMenu(String hql) {
		getSession().createSQLQuery(hql);
	}


}
