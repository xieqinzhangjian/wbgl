package xymz.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import xymz.dao.ClassifyDao;
import xymz.pojo.Classify;

@Repository("classifyDao")
public class ClassifyDaoImpl extends BaseDaoImpl<Classify> implements ClassifyDao {

	public List<Classify> queryAllparent() {
		Session session = getSession();
		String hql = " from Classify c where c.parent IS NULL";
		List<Classify> classifyList = session.createQuery(hql).list();//
		return classifyList;
	}

/*	public List<Classify> queryAllchildren() {
		Session session = getSession();
		String hql = " from Classify c where c.parent IS NOT NULL and c.status=0 ";
		List<Classify> classifyList = session.createQuery(hql).list();//
		return classifyList;

	}
*/
	@Override
	public Classify queryByText(String text) {
		Session session = getSession();
		String hql = " from Classify c where c.text=:text ";
		return (Classify) session.createQuery(hql).setParameter("text", text).uniqueResult();
	}





}
