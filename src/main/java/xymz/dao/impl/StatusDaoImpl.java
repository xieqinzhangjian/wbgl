package xymz.dao.impl;

import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import xymz.dao.StatusDao;
import xymz.pojo.Status;



@Repository("statusDao")
public class StatusDaoImpl extends BaseDaoImpl<Status> implements StatusDao  {

	@Override
	public Map<String, Integer> statistic() {
		
		return null;
	}

	@Override
	public Status queryByText(String text) {
		Session session = getSession();
		String hql="from Status s where s.text=:text ";
		return	(Status) session.createQuery(hql).setParameter("text", text).uniqueResult();	
	}

}
