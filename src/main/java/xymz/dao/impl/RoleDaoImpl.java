package xymz.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import xymz.dao.RoleDao;
import xymz.pojo.Role;

@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

	public List<Role> pageQuery(Role t, String hql) {
		Session session = getSession();
		List<Role> departmentList= session.createQuery(hql).setFirstResult((t.getPage()-1)*(t.getRows())).setMaxResults(t.getRows()).list();
		return departmentList;
	}

	@Override
	public List<Role> roleList(String hql) {
		return getSession().createQuery(hql).list();
	}
}
