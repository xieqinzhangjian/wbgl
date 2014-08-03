package xymz.dao;

import xymz.pojo.SellHardware;

public interface SellHardwareDao extends BaseDao<SellHardware>{
	public SellHardware queryByName(String wbName);
	
	
}
