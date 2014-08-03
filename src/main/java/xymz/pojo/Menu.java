package xymz.pojo;

import java.util.HashSet;
import java.util.Set;

public class Menu implements Comparable<Menu>{
	private Integer id;
	private Integer pid=0;
	private Menu parentMenu; //父菜单
	private String text;
	private String url;
	private String iconCls;
	private String state="open";
	private Set<Menu> children = new HashSet<Menu>(); //菜单里可能会有多个子菜单
	


	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Set<Menu> getChildren() {
		return children;
	}
	public void setChildren(Set<Menu> children) {
		this.children = children;
	}
	public Menu getParentMenu() {
		return parentMenu;
	}
	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	@Override
	public int compareTo(Menu o) {
		return o.getId()-this.id;
	}

}
