package xymz.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONWriter;

import xymz.pojo.SellHardware;

@Controller("sellHardwareAction")
@Scope("prototype")
public class SellHardwareAction extends BaseAction<SellHardware> {

	public void save() {
		try {
			writeJson(sellHardwareService.save(model));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dataGrid() {
		try {
			writeJson(sellHardwareService.dataGrid(model));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void delete() {
		try {
			writeJson(sellHardwareService.delete(model));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void edit() {
		try {
			writeJson(sellHardwareService.edit(model));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void saveChange() {
		try {
			writeJson(sellHardwareService.saveChange(model));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
