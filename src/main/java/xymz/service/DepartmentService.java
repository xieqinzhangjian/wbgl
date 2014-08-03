package xymz.service;

import java.util.List;

import xymz.pojo.DataGrid;
import xymz.pojo.Department;
import xymz.pojo.DepartmentTree;
import xymz.pojo.Json;
import xymz.pojo.Role;

public interface DepartmentService {
	
	public DataGrid dataGrid(Department department);
	public List<Department> getDepartmentTree();
	public Json save(Department department) ;
	public Json delete(Department department);
	public Json update(Department department);
	public Department byId(int id);
	public Json departmentList();
	public Json isParentOk(Department department);

	
}
