package xymz.service;

import java.util.Set;

import xymz.pojo.Json;
import xymz.pojo.Privilege;
import xymz.pojo.User;

public interface PrivilegeService {

	public Set<Privilege> getPrivilegeTree();
	public Json edit(String ids);
	
	public Json approve(String url,User user);

}
