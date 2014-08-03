package xymz.pojo;

import java.util.Date;

public class MoviePayLog {

	private Integer id;
	private Date payDate;//续费日期
	private Date endUseDate;
	private String cost;//续了多少费用
	private String remarks;//备注
	private String userName;
	
	
	
	public Date getEndUseDate() {
		return endUseDate;
	}
	public void setEndUseDate(Date endUseDate) {
		this.endUseDate = endUseDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
