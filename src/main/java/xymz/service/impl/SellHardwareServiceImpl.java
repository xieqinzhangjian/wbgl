package xymz.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import xymz.dao.SellHardwareDao;
import xymz.dao.UserDao;
import xymz.pojo.DataGrid;
import xymz.pojo.Json;
import xymz.pojo.SellHardware;
import xymz.service.SellHardwareService;

@Service("sellHardwareService")
public class SellHardwareServiceImpl implements SellHardwareService {

	@Resource
	private SellHardwareDao sellHardwareDao;

	@Override
	public Json save(SellHardware sellHardware) {
		
		String wbName = sellHardware.getWbName();
		SellHardware old = sellHardwareDao.queryByName(wbName);
		if(old!=null){
			Json j = new Json();
			old.setLastupdateDate(new Date());
			sellHardwareDao.save(old);
			j.setMsg("更新成功");
			return j;			
		}else{
			Json j = new Json();
			sellHardware.setLastupdateDate(new Date());
			sellHardwareDao.save(sellHardware);
			j.setMsg("更新成功");
			return j;				
		}
	}

	@Override
	public DataGrid dataGrid(SellHardware sellHardware) {
		DataGrid dg = new DataGrid();
		String fromClause = "from SellHardware s ";
		String whereClause = "";
		String orderByClause = "";

		Map<String, Object> params = new HashMap<String, Object>();

		if (sellHardware.getWbName() != null && !"".equals(sellHardware.getWbName())) {
			if ("".equals(whereClause)) {
				whereClause += " where s.wbName like :wbName";
			} else {
				whereClause += " and s.wbName like :wbName";

			}
			params.put("wbName", "%%" + sellHardware.getWbName() + "%%");
		}

		// 排序
		if (sellHardware.getSort() != null && !"".equals(sellHardware.getSort())) {
			orderByClause += " order by " + sellHardware.getSort() + " " + sellHardware.getOrder();
		}

		String hql = fromClause + whereClause + orderByClause;

		List<SellHardware> sellHardwareList = sellHardwareDao.find(hql, params, sellHardware.getPage(), sellHardware.getRows());

		dg.setRows(sellHardwareList);

		return dg;
	}

	@Override
	public Json delete(SellHardware sellHardware) {
		Json j = new Json();
		try {
			sellHardwareDao.delete(sellHardware.getId());
			j.setMsg("删除成功");
			j.setSuccess(true);

			return j;
		} catch (Exception e) {
			j.setMsg("删除失败,请与管理员联系!");
			j.setSuccess(false);
			return j;
		}

	}

	@Override
	public Json edit(SellHardware model) {
		Json j = new Json();
		SellHardware oldSellHardware = sellHardwareDao.get(model.getId());
		
		oldSellHardware.setHardware_detail(model.getHardware_detail());
		oldSellHardware.setLastupdateDate(new Date());
		
		try {
			sellHardwareDao.update(oldSellHardware);
			j.setSuccess(true);
			j.setObj(sellHardwareDao.get(oldSellHardware.getId()));
			j.setMsg("更新成功");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("更新失败");
			return j;
		}

	}

	@Override
	public Json saveChange(SellHardware model) {
		Json j= new Json();
		SellHardware old =sellHardwareDao.get(model.getId());
		old.setHardware_change(model.getHardware_change());
		old.setLastupdateDate(new Date());
		
		try {
			sellHardwareDao.update(old);
			j.setSuccess(true);
			j.setObj(sellHardwareDao.get(model.getId()));
			j.setMsg("更新成功");
			return j;
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setObj(sellHardwareDao.get(model.getId()));
			j.setMsg("更新失败,请与管理员联系");
			return j;
			
		}
	}

}
