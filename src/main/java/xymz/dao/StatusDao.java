package xymz.dao;

import java.util.Map;

import xymz.pojo.Status;


public interface StatusDao extends BaseDao<Status> {
	
	public Map<String,Integer> statistic();
	public Status queryByText(String text);
}
