package xymz.dao;

import java.util.List;

import xymz.pojo.Privilege;

public interface PrivilegeDao extends BaseDao<Privilege> {

	public List<Privilege> privilegeTree(String hql);
}
