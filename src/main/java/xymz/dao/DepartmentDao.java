package xymz.dao;

import java.util.List;

import xymz.pojo.Department;


public interface  DepartmentDao extends BaseDao<Department>{
	
	public List<Department> getAllParent(String hql);
	public List<Department> pageQuery(Department department, String hql);
}
