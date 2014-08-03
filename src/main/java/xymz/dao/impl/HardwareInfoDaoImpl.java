package xymz.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import xymz.dao.HardwareInfoDao;
import xymz.pojo.DataGrid;
import xymz.pojo.HardwareInfo;
import xymz.pojo.SellHardware;

@Repository("hardwareInfoDao")
public class HardwareInfoDaoImpl extends BaseDaoImpl<HardwareInfo> implements HardwareInfoDao {

	@Override
	public List<SellHardware> queryBySellHardware(SellHardware sellHardware) {

		String hql = "from HardwareInfo s where s.sellHardware=:sellHardware";

		return getSession().createQuery(hql).setParameter("sellHardware", sellHardware).list();
	}

	@Override
	public Long countBySellHardware(SellHardware sellHardware) {
		
		String hql = "select count(*) from HardwareInfo s where s.sellHardware=:sellHardware";
		return (Long) getSession().createQuery(hql).setParameter("sellHardware", sellHardware).uniqueResult();
	}

}
