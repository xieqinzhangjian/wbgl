package xymz.model;

import java.util.Date;

import xymz.pojo.WB;

public class ProblemSimple {
	private int id;
	
	private String userName;//用户名
	
	private String statusName;//问题状态
	private String appointName;//委托人姓名
	private String classifyName;//所属分类
	private String content;//所属分类
	
	
	
	private String title;//问题标题
	private Date createDate;//问题的创建日期
	
	private String wbName;//每个问题都有所属的网吧
	private String wbTel;
	private String wbAddr;
	private String contact;
	

	private String createDateString;
	private String lastUpdateDateString;
	
	
	
	
	

	public String getWbTel() {
		return wbTel;
	}

	public void setWbTel(String wbTel) {
		this.wbTel = wbTel;
	}

	public String getWbAddr() {
		return wbAddr;
	}

	public void setWbAddr(String wbAddr) {
		this.wbAddr = wbAddr;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLastUpdateDateString() {
		return lastUpdateDateString;
	}

	public void setLastUpdateDateString(String lastUpdateDateString) {
		this.lastUpdateDateString = lastUpdateDateString;
	}
	//private String content;//问题内容和处理方法`
	private Date lastUpdateTime;// 最后更新时间（主题发表时间或最后回复的时间）
	//private WB wb;//每个问题都有所属的网吧
	
	
	
	
	public String getStatusName() {
		return statusName;
	}

	public String getCreateDateString() {
		return createDateString;
	}

	public void setCreateDateString(String createDateString) {
		this.createDateString = createDateString;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {

		this.createDate = createDate;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getClassifyName() {
		return classifyName;
	}
	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}



	public String getAppointName() {
		return appointName;
	}
	public void setAppointName(String appointName) {
		this.appointName = appointName;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
	
		
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getWbName() {
		return wbName;
	}
	public void setWbName(String wbName) {
		this.wbName = wbName;
	}
	
	
}
