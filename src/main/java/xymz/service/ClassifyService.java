package xymz.service;

import java.util.List;

import xymz.model.ClassifySimple;
import xymz.pojo.Classify;
import xymz.pojo.Json;

public interface ClassifyService {

	public Json save(Classify classify);
	
	public List<Classify> query();
	public Classify get(int id);
	public Json delete(int id);
	public Json update(Classify classify);
	public List<Classify> queryAllparent();
	//public List<Classify> queryAllchildren();
}
