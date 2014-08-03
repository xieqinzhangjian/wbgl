package xymz.service;

import xymz.model.WBSimple;
import xymz.pojo.WB;

public interface WBservice{
	
	public WBSimple queryByName(String name);
	public void save(WB wb);
	
	public WB get(int id);
}
