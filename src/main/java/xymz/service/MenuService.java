package xymz.service;

import java.util.List;
import java.util.Set;

import xymz.model.UserSimple;
import xymz.pojo.Menu;
import xymz.pojo.User;

public interface MenuService {
	public Set<Menu> AllParent(User user);
	public void save(Menu menu);
	public void update(Menu menu);
	public void delete(Integer id);
	public Menu get(Integer id);
	public List<Menu> query();
}
