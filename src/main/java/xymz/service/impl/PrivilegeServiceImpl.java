package xymz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import xymz.dao.PrivilegeDao;
import xymz.dao.UserDao;
import xymz.pojo.Json;
import xymz.pojo.Privilege;
import xymz.pojo.Role;
import xymz.pojo.User;
import xymz.service.PrivilegeService;

@Service("privilegeService")
public class PrivilegeServiceImpl implements PrivilegeService{
	
	@Resource
	private PrivilegeDao privilegeDao;
	@Resource
	private UserDao userDao;

	@Override
	public Set<Privilege> getPrivilegeTree() {
		String hql="from Privilege";
		List<Privilege>  privilegeList = privilegeDao.privilegeTree(hql);
		
		Set<Privilege> privilegeSet =new TreeSet<Privilege>();
		privilegeSet.addAll(privilegeList);
		
		return privilegeSet;
	}

	@Override
	public Json edit(String ids) {
		
		
		return null;
	}

	@Override
	public Json approve(String url,User user) {
		Json j = new Json();
		if("admin".equals(user.getName())){
			j.setSuccess(true);
			return j;
		}
		
		
		List<String> privilegeUrlList = new ArrayList<String>();
		Set<Role> roleSet = user.getRoles();
		for (Role role : roleSet) {
			Set<Privilege> privilegeSet = role.getPrivilegeSet();
			for (Privilege privilege : privilegeSet) {
				privilegeUrlList.add(privilege.getUrl());
			}
		}
		if(privilegeUrlList.contains(url)){
			j.setSuccess(true);
		}else{
			j.setSuccess(false);
			j.setMsg("没有权限,请与管理员联系");
		}

		return j;
	}

}
