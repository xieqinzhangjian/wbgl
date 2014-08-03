package xymz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import xymz.dao.DepartmentDao;
import xymz.pojo.DataGrid;
import xymz.pojo.Department;
import xymz.pojo.DepartmentSimple;
import xymz.pojo.Json;
import xymz.service.DepartmentService;
import xymz.utils.HqlHelper;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

	@Resource
	private DepartmentDao departmentDao;

	@SuppressWarnings("unchecked")
	@Override
	public DataGrid dataGrid(Department department) {
		DataGrid dg = new DataGrid();
		String fromClause = "from Department p ";
		String whereClause = "";
		String orderByClause = "";

		Map<String, Object> parameters = new HashMap<String, Object>();

		// 条件
		if (department.getText() != null && !"".equals(department.getText().trim())) {
			whereClause+=" where p.text like :text ";
			parameters.put("text", "%%"+department.getText()+"%%");
		}

		// 排序
		if (department.getSort() != null && !"".equals(department.getSort())) {
			orderByClause+=" order by " + department.getSort() + " " + department.getOrder();
		}

		String hql=fromClause+whereClause+orderByClause;
		List<Department> departmentList = departmentDao.find(hql,parameters,department.getPage(),department.getRows());

		if (departmentList == null || departmentList.size() == 0) {
			dg.setSuccess(false);

		}

		List<DepartmentSimple> departmentSimpleList = new ArrayList<DepartmentSimple>();
		for (Department department2 : departmentList) {
			DepartmentSimple departmentSimple = new DepartmentSimple();
			departmentSimple.setId(department2.getId());
			departmentSimple.setText(department2.getText());
			if (department2.getParent() != null) {
				departmentSimple.setParent(department2.getParent().getText());
				departmentSimple.setParentId(department2.getParent().getId());

			}

			departmentSimple.setFax(department2.getFax());
			departmentSimple.setTel(department2.getTel());

			departmentSimpleList.add(departmentSimple);
		}

		// 总记录数
		dg.setTotal(departmentDao.count("select count(*) "+hql,parameters));
		dg.setRows(departmentSimpleList);

		return dg;
	}

	public List<Department> getDepartmentTree() {
		String hql = "from Department d where d.parent is null";

		// List<DepartmentTree> departmentTreeList = new
		// ArrayList<DepartmentTree>();

		List<Department> departmentList = departmentDao.getAllParent(hql);

		return departmentList;
	}

	@Override
	public Json save(Department department) {

		// 如果ID不等于空,说明有上级部门,所以要添加上级部门
		if (department.getId() != null) {
			department.setParent(departmentDao.get(department.getId()));
		}
		DepartmentSimple departmentSimple = new DepartmentSimple();

		departmentDao.save(department);
		Json j = new Json();

		departmentSimple.setId(department.getId());
		departmentSimple.setText(department.getText());
		departmentSimple.setFax(department.getFax());
		departmentSimple.setTel(department.getTel());
		if (department.getParent() != null) {
			departmentSimple.setParent(department.getParent().getText());
		}

		j.setSuccess(true);
		j.setMsg("添加部门成功!");
		j.setObj(departmentSimple);

		return j;

	}

	public Json delete(Department department) {
		Json j = new Json();
		String[] ids = department.getIds().split(",");
		for (String id : ids) {
			Integer newId = Integer.parseInt(id);

			Department newDepartment = departmentDao.get(newId);

			// 如果获取到 的部门没有子类,那么可以直接删除
			if (newDepartment.getChildren().size() == 0) {

				departmentDao.delete(newId);
				j.setSuccess(true);
				j.setMsg("删除成功");
			} else {
				j.setSuccess(false);
				j.setMsg("［" + newDepartment.getText() + "］" + "此部门下有子部门,请先把子部门删除后,再删除此部门");
				return j;
			}
		}
		return j;

	}

	@Override
	public Json update(Department department) {
		Json j = new Json();

		if (department.getText() == null || "".equals(department.getText().trim())) {
			j.setMsg("信息不完整,请检查");
			j.setSuccess(false);
			return j;
		}

		// 通过ID获取数据库中的对象
		Department newObj = departmentDao.get(department.getId());
		newObj.setTel(department.getTel());
		newObj.setText(department.getText());
		newObj.setFax(department.getFax());

		if (department.getParentId() != null) {
			newObj.setParent(departmentDao.get(department.getParentId()));
		}

		departmentDao.update(newObj);
		j.setSuccess(true);
		j.setMsg("更新成功");
		return j;
	}

	public Department byId(int id) {
		return departmentDao.get(id);

	}

	public Json departmentList() {
		System.out.println("departmentList---service执行了吗?");

		Json j = new Json();
		List<Department> departmentList = departmentDao.getAllParent("from Department");

		if (departmentList.isEmpty()) {
			j.setSuccess(false);
			j.setMsg("没有部门,请先添加部门!");
		} else {
			j.setSuccess(true);

		}
		return j;

	}

	@Override
	public Json isParentOk(Department department) {

		Json j = new Json();
		Department depart = departmentDao.get(department.getId());
		Department parentDepart = departmentDao.get(department.getParentId());

		Set<Department> departSet = depart.getChildren(); // 总经办

		List<Department> dep = new ArrayList<Department>();

		if (departSet.size() != 0) {
			recursionDepartment(departSet, dep);

		}
		if (dep.contains(parentDepart)) {
			j.setSuccess(false);
			j.setMsg("抱歉,不能选择<font color='red'>" + parentDepart.getText() + "</font>作为<font color='red'>" + depart.getText() + "</font>的上级部门,因为这两个部门已经存在上下级关系");
		} else {
			j.setSuccess(true);
		}
		return j;

	}

	public void recursionDepartment(Set<Department> departSet, List<Department> dep) {

		for (Department department : departSet) {
			dep.add(department);
			if (department.getChildren().size() != 0) {
				recursionDepartment(department.getChildren(), dep);
			}

		}
		System.out.println("dep.size()=" + dep.size());
	}

}
