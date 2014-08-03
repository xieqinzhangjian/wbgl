package xymz.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import xymz.pojo.HardwareInfo;

@Controller
@Scope("prototype")
public class HardwareInfoAction extends BaseAction<HardwareInfo> {

	public void queryBySellHardware() {
		try {
			writeJson(hardwareInfoService.queryBySellHardware(model));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
