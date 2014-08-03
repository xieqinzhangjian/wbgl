package xymz.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import xymz.dao.PrivilegeDao;
import xymz.pojo.Privilege;


@Repository("privilegeDao")
public class PrivilegeDaoImpl  extends BaseDaoImpl<Privilege> implements PrivilegeDao {

	@Override
	public List<Privilege> privilegeTree(String hql) {
		Session session = getSession();	
		return session.createQuery(hql).list();
	}

}