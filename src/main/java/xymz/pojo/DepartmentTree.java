package xymz.pojo;

import java.util.ArrayList;
import java.util.List;

public class DepartmentTree {

	private Long id;
	private String text;
	List<Department> children = new ArrayList<Department>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<Department> getChildren() {
		return children;
	}
	public void setChildren(List<Department> children) {
		this.children = children;
	}

	
	
}
