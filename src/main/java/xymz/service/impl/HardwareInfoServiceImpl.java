package xymz.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import xymz.dao.HardwareInfoDao;
import xymz.dao.SellHardwareDao;
import xymz.pojo.DataGrid;
import xymz.pojo.HardwareInfo;
import xymz.service.HardwareInfoService;

@Service
public class HardwareInfoServiceImpl implements HardwareInfoService {
	@Resource
	private HardwareInfoDao hardwareInfoDao;

	@Override
	public DataGrid queryBySellHardware(HardwareInfo hardwareInfo) {

		DataGrid dg = new DataGrid();

		dg.setObj(hardwareInfoDao.get(hardwareInfo.getId()));
		return dg;

	}
}
