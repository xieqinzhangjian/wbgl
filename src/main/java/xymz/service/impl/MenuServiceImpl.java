package xymz.service.impl;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import xymz.dao.MenuDao;
import xymz.pojo.Menu;
import xymz.pojo.User;
import xymz.service.MenuService;
import xymz.utils.GetMenuUtil;

@Service("menuService")
public class MenuServiceImpl implements MenuService {
	@Resource
	private MenuDao menuDao;

	public Set<Menu> AllParent(User user) {

		List<Menu> menuList = GetMenuUtil.AllParent(user);
		Set<Menu> menuSet = new TreeSet();
		
		menuSet.addAll(menuList);
		return menuSet;

	}

	@Override
	public void save(Menu menu) {
		if (menu.getPid() != 0) {
			menu.setParentMenu(menuDao.get(menu.getPid()));

			menuDao.save(menu);
		} else {
			menuDao.save(menu);
		}

	}

	@Override
	public void update(Menu menu) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Menu get(Integer id) {
		return menuDao.get(id);
	}

	@Override
	public List<Menu> query() {
		// TODO Auto-generated method stub
		return null;
	}

}
