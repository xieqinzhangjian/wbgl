package xymz.dao;

import java.util.List;

import xymz.pojo.Classify;

public interface ClassifyDao extends BaseDao<Classify> {

	public List<Classify> queryAllparent();
	//public List<Classify> queryAllchildren();
	public Classify queryByText(String text);
}
