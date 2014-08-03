package xymz.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SellMovie {
	private Integer id;
	private String regNumber;
	private WB wb ;//所属网吧
	private String agent;//代理商
	private Date startUseDate; //计费日期
	private Date endUseDate; //到期时间
	private String isCharge;//是否收费
	private String isMyGameSoft;//是否用我们的游戏
	private String cost;//费用
	private String wbTel;//联系电话
	private String wbName;
	private String state; //过期,激活,试用,已流失
	private String contact; //
	private String scope; //

	private Date paymentDate;
	
	
	
	
	//不参与映射
	
	private String addr;//费用
	private int page;
	private int rows;
	private String sort;
	private String order;
	private Date payDate;//续费日期
	private String remarks;//备注
	

	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getWbTel() {
		return wbTel;
	}
	public void setWbTel(String wbTel) {
		this.wbTel = wbTel;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	//续费记录
	Set<MoviePayLog> moviePayLog = new HashSet<MoviePayLog>();

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Set<MoviePayLog> getMoviePayLog() {
		return moviePayLog;
	}
	public void setMoviePayLog(Set<MoviePayLog> moviePayLog) {
		this.moviePayLog = moviePayLog;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRegNumber() {
		return regNumber;
	}
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}
	public WB getWb() {
		return wb;
	}
	public void setWb(WB wb) {
		this.wb = wb;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public Date getStartUseDate() {
		return startUseDate;
	}
	public void setStartUseDate(Date startUseDate) {
		this.startUseDate = startUseDate;
	}
	public Date getEndUseDate() {
		return endUseDate;
	}
	public void setEndUseDate(Date endUseDate) {
		this.endUseDate = endUseDate;
	}
	public String getIsCharge() {
		return isCharge;
	}
	public void setIsCharge(String isCharge) {
		this.isCharge = isCharge;
	}
	public String getIsMyGameSoft() {
		return isMyGameSoft;
	}
	public void setIsMyGameSoft(String isMyGameSoft) {
		this.isMyGameSoft = isMyGameSoft;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getWbName() {
		return wbName;
	}
	public void setWbName(String wbName) {
		this.wbName = wbName;
	}
	
	
	
	
}
