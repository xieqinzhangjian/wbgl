package xymz.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import xymz.model.UserSimple;
import xymz.pojo.Classify;
import xymz.pojo.Menu;
import xymz.pojo.Privilege;
import xymz.pojo.Role;
import xymz.pojo.User;
@SuppressWarnings("unused")
public class GetMenuUtil {


	public static List<Menu> AllParent(User user) {
		Configuration cf = new Configuration().configure("hibernate.config.xml");
		
		Session session = cf.buildSessionFactory().openSession();
		
		String hql = null;
		hql = "from Menu m where m.parentMenu is NULL";
	
		// 为了减少不必要的查询,建了一个没有set集合的javabean,因为只查询父,没必要把子类都查询出来
		@SuppressWarnings("unchecked")
		
		List<Menu> menuList = session.createQuery(hql).list();
		// 如果还没登录发出的请求,直接返回空
		if (user == null) {
			return null;
		}

		// 如果是admin用户,默认有所有菜单权限
		if (user.getName().equals("admin")) {
			return menuList;
		} else {

			List<String> privilegeTextList = new ArrayList<String>();
			Set<Role> roleSet = user.getRoles();
			for (Role role : roleSet) {
				Set<Privilege> privilegeSet = role.getPrivilegeSet();
				for (Privilege privilege : privilegeSet) {
					privilegeTextList.add(privilege.getText());
				}
			}
			fillterMenu(menuList,privilegeTextList);
			return menuList;
		}



	}

	public static void fillterMenu(List<Menu> menuList, List<String> privilegeTextList) {
		Iterator<Menu> menuIterator = menuList.iterator();
		while (menuIterator.hasNext()) {
			Menu menu = menuIterator.next();
			if(!privilegeTextList.contains(menu.getText())){
				menuIterator.remove();
			}else{
				Set<Menu>	childrenMenuSet = menu.getChildren();
				if(childrenMenuSet.size()!=0){
					Iterator<Menu> childrenMenuIterator = childrenMenuSet.iterator();
						while(childrenMenuIterator.hasNext()){
							Menu childrenMenu = childrenMenuIterator.next();
							if(!privilegeTextList.contains(childrenMenu.getText())){
								childrenMenuIterator.remove(); //这个操作会导致
							}
							
						}
				}			
			}
		}

	}
	
	
	public static List<Classify> AllParent() {
	Configuration cf = new Configuration().configure("hibernate.config.xml");
		
		Session session = cf.buildSessionFactory().openSession();
		
		String hql = " from Classify c where c.parent IS NULL";
		List<Classify> cList = session.createQuery(hql).list();
		fillterClassify(cList);
		
		return cList;
		
	}
	

	public static void fillterClassify(List<Classify> classifyList) {
		Iterator<Classify> classifyIterator = classifyList.iterator();
		while (classifyIterator.hasNext()) {
			Classify classify = classifyIterator.next();
			if (classify.getStatus() == 1) {
				classifyIterator.remove();

			}
			Set<Classify> childrenClassifySet = classify.getChildren();
			if (childrenClassifySet.size() != 0) {
				Iterator<Classify> childrenClassifyIterator = childrenClassifySet.iterator();
				while (childrenClassifyIterator.hasNext()) {
					Classify childrenClassify = childrenClassifyIterator.next();
					if (childrenClassify.getStatus() == 1) {
						childrenClassifyIterator.remove(); // 这个操作会导致
					}
					
					if(childrenClassify.getChildren().size()!=0){
						Set<Classify> sunClassifySet= childrenClassify.getChildren();
						
						Iterator<Classify> sunClassifyIterator = sunClassifySet.iterator();
							while (sunClassifyIterator.hasNext()) {
								Classify sunClassify = sunClassifyIterator.next();
								if(sunClassify.getStatus()!=0){
									sunClassifyIterator.remove();
								}
								
							}
						
					}

				}
			}
		}

	}
	
	public static void ddClassify(List<Classify> classifyList) {
		Iterator<Classify> classifyIterator = classifyList.iterator();
		while (classifyIterator.hasNext()) {
			Classify classify = classifyIterator.next();
			if (classify.getStatus() == 1) {
				classifyIterator.remove();

			}
			Set<Classify> childrenClassifySet = classify.getChildren();
			if (childrenClassifySet.size() != 0) {
					List<Classify> cList =new ArrayList<Classify>();
					cList.addAll(childrenClassifySet);
					ddClassify2(cList);
			}
		}

	}
	
	public static void ddClassify2(List<Classify> classifyList) {
		Iterator<Classify> classifyIterator = classifyList.iterator();
		while (classifyIterator.hasNext()) {
			Classify classify = classifyIterator.next();
			if (classify.getStatus() == 1) {
				classifyIterator.remove();
			}
		}

	}
	
	
	
}
