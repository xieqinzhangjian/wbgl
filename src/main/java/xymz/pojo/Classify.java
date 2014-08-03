package xymz.pojo;

import java.util.HashSet;
import java.util.Set;

public class Classify {

	private int id;
	private String text;
	/*
	 * private String classifyColor; private String description; private Integer count=0; private Integer total;
	 */
	private Classify parent; // 分类的上级
	private String parentName; // 分类的上级
	private Set<Classify> children = new HashSet<Classify>(); // 分类的上级
	private Integer status=0;
	
	
	


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	private Integer parentId;
	
	
	
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Classify getParent() {
		return parent;
	}

	public void setParent(Classify parent) {
		this.parent = parent;
	}

	public Set<Classify> getChildren() {
		return children;
	}

	public void setChildren(Set<Classify> children) {
		this.children = children;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}


}
