package xymz.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import xymz.dao.SellHardwareDao;
import xymz.pojo.SellHardware;

@Repository("sellHardwareDao")
public class SellHardwareDaoImpl extends BaseDaoImpl<SellHardware> implements SellHardwareDao {

	public SellHardware queryByName(String wbName) {
		Session session = sessionFactory.getCurrentSession();
		SellHardware s = (SellHardware) session.createQuery("FROM SellHardware w where w.wbName =:wbName")//
				.setString("wbName", wbName).uniqueResult();


		return s;
	}
}
