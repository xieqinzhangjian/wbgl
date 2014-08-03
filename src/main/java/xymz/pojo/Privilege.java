package xymz.pojo;

import java.util.Set;
import java.util.TreeSet;

/**
 *  需要设置 权限自关联关系
 */
public class Privilege implements java.io.Serializable,Comparable<Privilege> {

	private static final long serialVersionUID = -4041846212302855579L;

	private Integer id;
	private String text;
	private String url;
	private boolean isleft;
	private boolean checked;
	/*
	 * 如果当前菜单是子菜单,则依赖的父菜单会存储到 parent中
	 * */
	private Privilege parent;
	private String stat="open";
	/*
	 * 如果当前菜单是父菜单,则查询的父菜单相应的子菜单存储到 set集合中
	 * */
	
	private Set<Privilege> children=new TreeSet<Privilege>();
	private Set<Role> roleSet=new TreeSet<Role>();


/**/
	
	
	public Integer getId() {
		return id;
	}
	public Set<Role> getRoleSet() {
		return roleSet;
	}
	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public boolean getChecked(){
		return checked;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public boolean isIsleft() {
		return isleft;
	}
	public void setIsleft(boolean isleft) {
		this.isleft = isleft;
	}
	public Privilege getParent() {
		return parent;
	}
	public void setParent(Privilege parent) {
		this.parent = parent;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public Set<Privilege> getChildren() {
		return children;
	}
	public void setChildren(Set<Privilege> children) {
		this.children = children;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int compareTo(Privilege o) {
		
		return o.getId()-this.id;
	}
	

}