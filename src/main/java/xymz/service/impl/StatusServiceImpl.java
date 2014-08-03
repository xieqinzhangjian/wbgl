package xymz.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import xymz.dao.StatusDao;
import xymz.pojo.Json;
import xymz.pojo.Status;
import xymz.service.StatusService;

@Service("statusService")
public class StatusServiceImpl implements StatusService{

	@Resource
	private StatusDao statusDao;

	@Override
	public void save(Status t) {
		statusDao.save(t);
		
	}

	@Override
	public void update(Status t) {
		statusDao.update(t);
		
	}

	@Override
	public void delete(int id) {
		statusDao.delete(id);
		
	}

	@Override
	public Status get(int id) {
		// TODO Auto-generated method stub
		return statusDao.get(id);
	}

	@Override
	public List<Status> query() {
		List<Status> statusList = statusDao.query();
		return statusList;

	}

	@Override
	public Status queryByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
