package xymz.dao;

import xymz.pojo.WB;

public interface WBDao extends BaseDao<WB>{
	public WB queryByName(String name);

}
