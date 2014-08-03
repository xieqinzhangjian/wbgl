package xymz.model;

import java.util.HashSet;
import java.util.Set;

public class ClassifySimple {
	private int id;
	private String text;
	
	private Set<ClassifySimple> children = new HashSet<ClassifySimple>(); //分类的上级

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

	public Set<ClassifySimple> getChildren() {
		return children;
	}

	public void setChildren(Set<ClassifySimple> children) {
		this.children = children;
	}

}
