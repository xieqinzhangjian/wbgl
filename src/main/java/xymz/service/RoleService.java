package xymz.service;

import java.util.List;
import java.util.Set;

import xymz.model.RoleSimple;
import xymz.pojo.DataGrid;
import xymz.pojo.Json;
import xymz.pojo.Privilege;
import xymz.pojo.Role;
import xymz.pojo.User;

public interface RoleService {

	public DataGrid dataGrid(Role role);
	public Json roleList();
	public Json save(Role role) ;
	public Json delete(Role role);
	public Json update(Role role);
	public List<RoleSimple> roleListByDeperId(Role role);
	public Json editPrivilege(Role role);
	
	public Json rolePrivilege(Role role);
	
	public Set<Privilege> privilegeByRolo(Role role);
	
	public List<Privilege> getPrivilegeTree();
	
	
	
	
}
