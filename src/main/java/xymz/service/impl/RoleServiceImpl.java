package xymz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import xymz.dao.DepartmentDao;
import xymz.dao.MenuDao;
import xymz.dao.PrivilegeDao;
import xymz.dao.RoleDao;
import xymz.model.RoleSimple;
import xymz.pojo.DataGrid;
import xymz.pojo.Department;
import xymz.pojo.Json;
import xymz.pojo.Menu;
import xymz.pojo.Privilege;
import xymz.pojo.Role;
import xymz.pojo.User;
import xymz.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleDao roleDao;
	@Resource
	private DepartmentDao departmentDao;

	@Resource
	private PrivilegeDao privilegeDao;
	@Resource
	private MenuDao menuDao;

	public DataGrid dataGrid(Role role) {

		DataGrid dg = new DataGrid();
		String fromClause = "from Role r ";
		String whereClause = "";
		String orderByClause = "";

		Map<String, Object> parameters = new HashMap<String, Object>();

		// 条件
		if (role.getText() != null && !"".equals(role.getText().trim())) {
			whereClause += "where r.text like :text";
			parameters.put("text", "%%" + role.getText() + "%%");
		}

		// 排序
		if (role.getSort() != null && !"".equals(role.getSort())) {
			orderByClause += " order by " + role.getSort() + " " + role.getOrder();
		}

		String hql = fromClause + whereClause + orderByClause;
		List<Role> roleList = roleDao.find(hql, parameters, role.getPage(), role.getRows());

		List<RoleSimple> roleSimpleList = new ArrayList<RoleSimple>();
		for (Role role2 : roleList) {
			RoleSimple roleSimple = new RoleSimple();
			roleSimple.setId(role2.getId());
			roleSimple.setText(role2.getText());
			if (role2.getDepartment() != null) {
				roleSimple.setDepartment(role2.getDepartment().getText());
				roleSimple.setDepartmentId(role2.getDepartment().getId());
			}
			if (role2.getDescription() != null & !"".equals(role2.getDescription())) {
				roleSimple.setDescription(role2.getDescription());
			}
			roleSimpleList.add(roleSimple);
		}

		// 总记录数
		dg.setTotal(roleDao.count("select count(*) " + hql, parameters));
		dg.setRows(roleSimpleList);

		return dg;
	}

	public Json roleList() {
		Json j = new Json();
		List<Role> roleList = roleDao.roleList("from Role");

		if (roleList.isEmpty()) {
			j.setSuccess(false);
			j.setMsg("没有岗位,请先添加岗位!");
		} else {
			j.setSuccess(true);

		}
		return j;

	}

	@Override
	public Json save(Role role) {

		Json j = new Json();
		if (role.getDepartmentId() == null || role.getDepartmentId() == 0 || role.getText() == null || "".equals(role.getText().trim())) {
			j.setMsg("信息不完整,请检查");
			j.setSuccess(false);
			return j;
		}

		// 设置岗位所属部门
		Department department = departmentDao.get(role.getDepartmentId());
		role.setDepartment(department);
		roleDao.save(role);

		RoleSimple roleSimple = new RoleSimple();

		j.setMsg("添加成功");
		j.setSuccess(true);
		roleSimple.setDepartment(department.getText());
		roleSimple.setText(role.getText());
		if (role.getDescription() != null) {
			roleSimple.setDescription(role.getDescription());
		}

		j.setObj(roleSimple);
		return j;
	}

	public Json delete(Role role) {
		Json j = new Json();
		String[] ids = role.getIds().split(",");
		for (String id : ids) {
			Integer newId = Integer.parseInt(id);
			// 如果获取到 的部门没有子类,那么可以直接删除
			roleDao.delete(newId);
		}
		j.setSuccess(true);
		j.setMsg("删除成功");

		return j;

	}

	@Override
	public Json update(Role role) {
		Json j = new Json();
		if (role.getDepartmentId() == null || role.getDepartmentId() == 0 || role.getText() == null || "".equals(role.getText().trim())) {
			j.setMsg("信息不完整,请检查");
			j.setSuccess(false);
			return j;
		}
		Role oldRol = roleDao.get(role.getId());

		oldRol.setDepartment(departmentDao.get(role.getDepartmentId()));
		oldRol.setText(role.getText());
		oldRol.setDescription(role.getDescription());

		roleDao.update(oldRol);

		j.setSuccess(true);
		j.setMsg("修改成功");
		return j;
	}

	@Override
	public List<RoleSimple> roleListByDeperId(Role role) {

		System.out.println(role.getDepartmentId() == null);

		if (role.getDepartmentId() == 0) {
			return new ArrayList<RoleSimple>();
		} else {
			Department depart = departmentDao.get(role.getDepartmentId());
			Set<Role> roleSet = depart.getRoles();
			List<RoleSimple> roleSimpleList = new ArrayList<RoleSimple>();

			if (!roleSet.isEmpty()) {
				for (Role role2 : roleSet) {
					RoleSimple roleSimple = new RoleSimple();
					roleSimple.setId(role2.getId());
					roleSimple.setText(role2.getText());
					roleSimpleList.add(roleSimple);
				}

			}
			return roleSimpleList;

		}

	}

	// 给岗位设置权限
	@Override
	public Json editPrivilege(Role role) {
		Json j = new Json();
		Role oldrole = roleDao.get(role.getId());

		Set<Privilege> privilegeSet = oldrole.getPrivilegeSet();
		privilegeSet.clear();

		String pids = role.getPrivilegeIds();

		String[] ids = null;
		if (pids != null && !"".equals(pids.trim())) {
			ids = pids.split(",");
			for (String pid : ids) {

				Privilege privilege = privilegeDao.get(Integer.parseInt(pid));
				dgPrivilege(privilegeSet, privilege);

			}
		}
		roleDao.update(oldrole);

		j.setSuccess(true);
		j.setMsg("修改成功");
		j.setObj(ids);
		return j;
	}

	public void dgPrivilege(Set<Privilege> privilegeSet, Privilege privilege) {
		privilegeSet.add(privilege);
		if (privilege.getParent() != null) {
			dgPrivilege(privilegeSet, privilege.getParent());
		}

	}

	public void initMenu() {

		// UPDATE menu SET parentId=NULL;
		// DELETE FROM menu;
		// 清空表
		String valueNull = "UPDATE menu SET parentId=NULL";
		String delMenu = "DELETE FROM menu";
		menuDao.delMenu(valueNull);
		menuDao.delMenu(delMenu);

		Menu sysMgrMenu = new Menu();

		sysMgrMenu.setText("系统管理");
		Set<Menu> sysMgrSet = sysMgrMenu.getChildren();

		Menu deparMgr = new Menu();
		deparMgr.setText("部门管理");
		deparMgr.setUrl("/sysMgr/departmentMgr/departmentMgr.jsp");
		sysMgrSet.add(deparMgr);

		Menu roleMgr = new Menu();
		roleMgr.setText("岗位管理");
		roleMgr.setUrl("/sysMgr/roleMgr/roleMgr.jsp");
		sysMgrSet.add(roleMgr);

		Menu userMgr = new Menu();
		userMgr.setText("用户管理");
		userMgr.setUrl("/sysMgr/userMgr/userMgr.jsp");
		sysMgrSet.add(userMgr);

		menuDao.save(sysMgrMenu);

		Menu affairMgrMenu = new Menu();
		sysMgrMenu.setText("事务管理");
		Set<Menu> affairMgrSet = affairMgrMenu.getChildren();

		Menu problemMenu = new Menu();
		problemMenu.setText("问题管理");
		problemMenu.setUrl("/problem/problemList.jsp");
		affairMgrSet.add(problemMenu);

		Menu statementMenu = new Menu();
		problemMenu.setText("报表");
		// problemMenu.setUrl("/problem/problemList.jsp");
		affairMgrSet.add(statementMenu);

		menuDao.save(affairMgrMenu);

	}

	@Override
	public Json rolePrivilege(Role role) {
		Json j = new Json();
		Role r = roleDao.get(role.getId());
		List<Integer> pids = new ArrayList<Integer>();

		Set<Privilege> privilegeSet = r.getPrivilegeSet();
		for (Privilege privilege : privilegeSet) {
			pids.add(privilege.getId());
		}
		j.setMsg("ok");
		j.setSuccess(true);
		j.setObj(pids);
		return j;
	}

	// 获取权限树
	@Override
	public Set<Privilege> privilegeByRolo(Role role) {
		Role r = roleDao.get(role.getId());

		// checked 为false的list
		List<Privilege> allPrivilegeList = getPrivilegeTree();

		// 从岗位取出来的checked 为true的list
		Set<Privilege> truePrivilegeList = r.getPrivilegeSet();

		Set<Privilege> allPrivilegeSet = new TreeSet<Privilege>();
		
		clprivilegeTreeHX(allPrivilegeList, truePrivilegeList);
		
		allPrivilegeSet.addAll(allPrivilegeList);
		return allPrivilegeSet;

	}

	// 处理权限树回显示
	public void clprivilegeTreeHX(List<Privilege> allPrivilegeList, Set<Privilege> truePrivilegeList) {
		if (allPrivilegeList != null) {
			Iterator<Privilege> privilegeListIt = allPrivilegeList.iterator();
			while (privilegeListIt.hasNext()) {
				Privilege privilege = privilegeListIt.next();
				if (privilege.getChildren().size() == 0) {
					if (truePrivilegeList.contains(privilege)) {
						privilege.setChecked(true);
					}
				} else {
					List<Privilege> newPrivilegeList = new ArrayList<Privilege>();
					newPrivilegeList.addAll(privilege.getChildren());
					clprivilegeTreeHX(newPrivilegeList, truePrivilegeList);

				}
			}
		}

	}

	// 获取所有权限树
	public List<Privilege> getPrivilegeTree() {
		String hql = "from Privilege p where p.parent is null";
		List<Privilege> privilegeList = privilegeDao.privilegeTree(hql);
		return privilegeList;
	}

}
