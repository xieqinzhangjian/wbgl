package xymz.service;

import java.util.List;

import xymz.pojo.Json;
import xymz.pojo.Status;

public interface StatusService{
	public void save(Status t);

	public void update(Status t);

	public void delete(int id);

	public Status get(int status);

	public List<Status> query();
	
	public Status queryByName(String name);
}
