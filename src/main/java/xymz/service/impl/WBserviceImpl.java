package xymz.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import xymz.dao.WBDao;
import xymz.model.WBSimple;
import xymz.pojo.WB;
import xymz.service.WBservice;

@Service("wBservice")
public class WBserviceImpl implements WBservice {

	@Resource
	private WBDao wBDao;
	
	public void save(WB wb){
		wBDao.save(wb);
	}

	public WBSimple queryByName(String name) {
		WB wb =wBDao.queryByName(name);
		
		if(wb!=null){
			WBSimple wbSimple= new WBSimple();
			BeanUtils.copyProperties(wb, wbSimple);
			return wbSimple;
		}
		return null;
		
		
		
	}

	@Override
	public WB get(int id) {
		return wBDao.get(id);
	}

}
