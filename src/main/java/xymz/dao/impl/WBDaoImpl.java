package xymz.dao.impl;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import xymz.dao.WBDao;
import xymz.pojo.WB;
@Repository("wBDao")
public class WBDaoImpl extends BaseDaoImpl<WB> implements WBDao {
	@Override
	public WB queryByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		 	List<WB> wbs  = session.createQuery("FROM WB w where w.name like:name")//
				.setString("name","%"+name+"%")//
				.list();
		 	
		 	if(wbs.size()!=0){
		 		System.out.println("wbs==null"+wbs==null);
		 		return  wbs.get(0);
		 	}
		 	return null;
	}
}