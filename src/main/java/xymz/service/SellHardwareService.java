package xymz.service;

import xymz.pojo.DataGrid;
import xymz.pojo.Json;
import xymz.pojo.SellHardware;

public interface SellHardwareService {
	public Json save(SellHardware sellHardware);
	public Json delete(SellHardware sellHardware);
	public DataGrid dataGrid(SellHardware sellHardware);
	public Json edit(SellHardware model);
	public Json saveChange(SellHardware model);
	
	
}
