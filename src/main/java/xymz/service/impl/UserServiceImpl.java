package xymz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import xymz.dao.DepartmentDao;
import xymz.dao.RoleDao;
import xymz.dao.UserDao;
import xymz.model.UserSimple;
import xymz.pojo.DataGrid;
import xymz.pojo.Json;
import xymz.pojo.Privilege;
import xymz.pojo.Role;
import xymz.pojo.User;
import xymz.service.UserService;
import xymz.utils.DigestUtils;
import xymz.utils.HqlHelper;
import xymz.utils.HtmlUtil;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;

	@Resource
	private RoleDao roleDao;
	@Resource
	private DepartmentDao departmentDao;

	public void sayHello() {
		System.out.println("sayHello--sayHello");

	}

	@Override
	public Json save(User user) {

		Json j = new Json();

		User u = userDao.userByname(user.getName());

		if (u != null) {
			j.setMsg("此用户已存在!");
			j.setSuccess(false);
			return j;

		}
		List<Integer> roleIds = user.getRoleIds();

		// 设置岗位
		if (roleIds.get(0) != null) {
			Set<Role> roleSet = user.getRoles();
			for (Integer roleId : roleIds) {
				Role role = roleDao.get(roleId);
				roleSet.add(role);
			}

			// 设置部门
			user.setDepartment(departmentDao.get(user.getDepartmentId()));
			// 设置密码
			user.setPassword(DigestUtils.md5Hex("1234"));
			user.setStatus(0);
			userDao.save(user);
			j.setMsg("添加成功");
			j.setSuccess(true);

			return j;

		} else {
			j.setMsg("请选择岗位!");
			j.setSuccess(false);

			return j;
		}

	}

	@Override
	public DataGrid dataGrid(User u) {
		String fromClause = "from User u ";
		String whereClause = "";
		String orderByClause = "";

		DataGrid dg = new DataGrid();
		// 1.准备参数
		Map<String, Object> parameters = new HashMap<String, Object>();
		// 条件
		if (u.getName() != null && !"".equals(u.getName().trim())) {
			whereClause += "where u.name like:name ";
			parameters.put("name", "%%" + u.getName() + "%%");

		}
		// 排序
		if (u.getSort() != null && !"".equals(u.getSort())) {
			orderByClause += " order by " + u.getSort() + " " + u.getOrder();
		}

		if (whereClause == "") {
			whereClause = " where u.status=:status";
			parameters.put("status", 0);

		} else {
			whereClause = "and u.status=:status";
			parameters.put("status", 0);
		}

		String hql = fromClause + whereClause + orderByClause;
		// 所有用户
		List<User> userList = userDao.find(hql, parameters, u.getPage(), u.getRows());

		List<UserSimple> userSimpleList = new ArrayList<UserSimple>();
		if (userList.size() > 0) {
			for (User user : userList) {
				UserSimple userSimple = userToUserSimple(user);

				userSimpleList.add(userSimple);
				/*
				 * BeanUtils.copyProperties(user, userSimple); userSimpleList.add(userSimple);
				 */

			}

		}
		// 查询总数
		Long count = userDao.count("select count(*) " + hql, parameters);
		dg.setTotal(count);
		dg.setRows(userSimpleList);

		return dg;
	}

	@Override
	public Json delete(User user) {
		Json j = new Json();
			try {
				User u = userDao.get(user.getId());
				u.setStatus(1); // 表示禁用此用户
				// userDao.delete(Integer.parseInt(user.getIds()));
				userDao.update(u);
				j.setMsg("删除成功");
				j.setSuccess(true);
				return j;
			} catch (Exception e) {
				j.setSuccess(false);
				j.setMsg("删除失败,请与管理员联系");
				return j;
			}
	}

	@Override
	public Json update(User user) {
		Json j = new Json();
		User oldUser = userDao.get(user.getId());
		oldUser.setName(user.getName());
		oldUser.setGender(user.getGender());
		oldUser.setEmail(user.getEmail());
		oldUser.setPhoneNumber(user.getPhoneNumber());

		List<Integer> roleIds = user.getRoleIds();

		if (roleIds.get(0) != null) {
			Set<Role> roleSet = oldUser.getRoles();
			roleSet.clear();

			for (Integer roleId : roleIds) {
				Role role = roleDao.get(roleId);
				roleSet.add(role);
			}
			oldUser.setDepartment(departmentDao.get(user.getDepartmentId()));

			System.out.println("执行updata的了阿门?");

			userDao.update(oldUser);

			j.setMsg("更新成功");
			j.setSuccess(true);

			return j;

		} else {
			j.setMsg("请选择岗位!");
			j.setSuccess(false);

			return j;
		}

	}

	@Override
	public List<UserSimple> query() {
		List<UserSimple> userSimples = new ArrayList<UserSimple>();
		List<User> userList = userDao.query();
		for (User user : userList) {
			UserSimple userSimple = new UserSimple();
			userSimple.setId(user.getId());
			userSimple.setName(user.getName());
			userSimples.add(userSimple);
		}
		return userSimples;
	}

	@Override
	public Json login(User user) {
		Json j = new Json();
		User u = userDao.userByname(user.getName());
		if (u == null) {
			j.setSuccess(false);
			j.setMsg("用户不存在!");
		} else {
			String newPs = DigestUtils.md5Hex(user.getPassword());
			String oldPs = u.getPassword();

			if (oldPs.equals(newPs)) {
				j.setSuccess(true);
				j.setMsg("登录成功");

				// 准备用户已有的权限url
				List<String> urlList = u.getUrlList();

				Set<Role> roles = u.getRoles();

				for (Role role : roles) {
					Set<Privilege> privilegeSet = role.getPrivilegeSet();
					for (Privilege privilege : privilegeSet) {
						urlList.add(privilege.getUrl());
					}
				}

				j.setObj(u);
			} else {
				j.setSuccess(false);
				j.setMsg("密码错误!");
			}
		}

		return j;
	}

	public UserSimple userToUserSimple(User user) {

		UserSimple userSimple = new UserSimple();

		userSimple.setId(user.getId());
		userSimple.setName(user.getName());
		userSimple.setGender(user.getGender());
		userSimple.setEmail(user.getEmail());
		userSimple.setPhoneNumber(user.getPhoneNumber());

		userSimple.setPictureURL(user.getPictureURL());
		if (user.getDepartment() != null) {
			userSimple.setDepartmentId(user.getDepartment().getId());
			userSimple.setDepartment(user.getDepartment().getText());
		}

		if (user.getRoles() != null) {
			Set<Role> roleSet = user.getRoles();
			for (Role role : roleSet) {
				userSimple.setRoleIds(role.getId());
			}

		}

		// 岗位处理
		String roles = "";
		if (user.getRoles().size() > 0) {
			Set<Role> roleSet = user.getRoles();
			int i = 0;
			for (Role role : roleSet) {
				i += 1;
				if (i == roleSet.size()) {
					roles += role.getText();
				} else {
					roles += role.getText() + ",";
				}
			}
		}
		userSimple.setRoles(roles);

		return userSimple;
	}

	@Override
	public User get(int id) {
		return userDao.get(id);
	}

	@Override
	public Json editPassword(int id, String password) {
		Json j = new Json();
		User user = userDao.get(id);
		user.setPassword(DigestUtils.md5Hex(password));
		userDao.save(user);
		j.setSuccess(true);
		j.setMsg("设置成功");

		return j;

	}
}
