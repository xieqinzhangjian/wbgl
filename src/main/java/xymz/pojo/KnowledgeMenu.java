package xymz.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class KnowledgeMenu implements Comparable<KnowledgeMenu>,Serializable{
	private Integer id;
	private Integer pid=0;
	private KnowledgeMenu parentMenu; //父菜单
	private String text;
	private String url;
	private String iconCls;
	private String state="open";
	
	private int topicTotal=0;
	
	private Set<KnowledgeMenu> children = new HashSet<KnowledgeMenu>(); //菜单里可能会有多个子菜单
	private Set<Topic> topicSet = new HashSet<Topic>(); //菜单里可能会有多个子菜单
	

	
	public int getTopicTotal() {
		return topicTotal;
	}
	public void setTopicTotal(int topicTotal) {
		this.topicTotal = topicTotal;
	}
	public Set<Topic> getTopicSet() {
		return topicSet;
	}
	public void setTopicSet(Set<Topic> topicSet) {
		this.topicSet = topicSet;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Set<KnowledgeMenu> getChildren() {
		return children;
	}
	public void setChildren(Set<KnowledgeMenu> children) {
		this.children = children;
	}
	public KnowledgeMenu getParentMenu() {
		return parentMenu;
	}
	public void setParentMenu(KnowledgeMenu parentMenu) {
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
	public int compareTo(KnowledgeMenu o) {
		return this.id-o.getId();
	}

}
