package xymz.pojo;

import java.util.HashMap;
import java.util.Map;


public class MenuSimple {

	private Integer id;
	private String text;
	private String iconCls;
	public Integer getId() {
		return id;
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
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	
	
	
	private String state;
	private Integer pid;
	private String ptext;
	private Map<String, Object> attributes= new HashMap<String, Object>();
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getPtext() {
		return ptext;
	}
	public void setPtext(String ptext) {
		this.ptext = ptext;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	
	
}