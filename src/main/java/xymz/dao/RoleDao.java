package xymz.dao;

import java.util.List;

import xymz.pojo.Role;

public interface RoleDao extends BaseDao<Role> {
	public List<Role> pageQuery(Role role,String hql);
	
	public List<Role> roleList(String hql);
}
