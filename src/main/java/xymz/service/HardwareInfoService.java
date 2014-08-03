package xymz.service;

import xymz.pojo.DataGrid;
import xymz.pojo.HardwareInfo;

public interface HardwareInfoService {

	public DataGrid queryBySellHardware(HardwareInfo hardwareInfo);
}
