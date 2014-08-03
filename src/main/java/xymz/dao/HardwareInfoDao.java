package xymz.dao;

import java.util.List;

import xymz.pojo.HardwareInfo;
import xymz.pojo.SellHardware;

public interface HardwareInfoDao extends BaseDao<HardwareInfo> {
	public List<SellHardware>  queryBySellHardware(SellHardware sellHardware);
	public Long  countBySellHardware(SellHardware sellHardware);
}
