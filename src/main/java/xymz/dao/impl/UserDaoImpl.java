package xymz.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import xymz.dao.UserDao;
import xymz.pojo.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

	public List<User> pageQuery(User u,String hql) {
		Session session = getSession();
		return session.createQuery(hql).setFirstResult((u.getPage()-1)*(u.getRows())).setMaxResults(u.getRows()).list();
	}

	@Override
	public User userByname(String name) {
		Session session = getSession();
		String hql="from User u where u.name=:name and u.status=:status";
		User u = (User) session.createQuery(hql).setString("name", name).setParameter("status", 2).uniqueResult();
		 return u;
	}

}
