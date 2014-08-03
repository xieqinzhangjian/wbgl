package xymz.action;

import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import xymz.model.PrivilegeSimple;
import xymz.model.RoleSimple;
import xymz.pojo.DataGrid;
import xymz.pojo.Department;
import xymz.pojo.DepartmentSimple;
import xymz.pojo.Json;
import xymz.pojo.Privilege;
import xymz.pojo.Role;
import xymz.pojo.User;

@Controller("roleAction")
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {

	public void dataGrid() {
		DataGrid dataGrid = roleService.dataGrid(model);
		try {
			writeJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void save() {
		Json j = roleService.save(model);
		try {
			writeJson(j);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void delete() {
		Json j = roleService.delete(model);
		try {
			writeJson(j);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void edit() {
		Json j = roleService.update(model);
		try {
			writeJson(j);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void roleList() {
		Json j = roleService.roleList();
		try {
			writeJson(j);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void roleListByDeperId() {
		List<RoleSimple> roleSimpleList = roleService.roleListByDeperId(model);

		System.out.println("roleSimpleList==" + roleSimpleList);
		try {
			writeJson(roleSimpleList);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void privilegeTree() {
		Set<Privilege> privilegeSet  = roleService.privilegeByRolo(model);
		try {
			writeJson(privilegeSet);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void editPrivilege() {
		try {
			writeJson(roleService.editPrivilege(model));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void rolePrivilege() {
		try {
			writeJson(roleService.rolePrivilege(model));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
