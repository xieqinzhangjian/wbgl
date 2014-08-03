package xymz.pojo;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class Problem {
	private int id;
	private WB wb;//每个问题都有所属的网吧
	private String wbName;
	
	private User user; //受理人
	private String userName;
	
	private String file;//附件
	private Date createDate;//问题的创建日期
	private String title;//问题标题
	
	
	private Classify classify;//所属分类
	private String classifyName;//所属分类
	
	private User appoint;//委托人
	private String appointName;
	
	private Status status;//问题状态
	private String statusName;//问题状态
	private String content;//问题内容和处理方法`
	
	

	
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	private Date lastUpdateTime;// 最后更新时间（主题发表时间或最后回复的时间）
	
	private Set<Reply> replySet =new TreeSet<Reply>();
	

//已处理(0),正在跟踪(1),提交线下(2),需要协助(3),关闭(4)
/*	private User appoint;//指派给谁
*/	
	
	
	//不参与映射的
	private int page;
	
	private String startDate;
	private String endDate;
	private String type;
	private int number;
	





	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	private int rows;
	private String sort;
	private String order;
	private String ids;
	
	private String text;//网吧名称
	private String contact; //网吧联系人
	private String wbTel;//网吧电话
	private String addr;//网吧地址
	
	
	
	//查询条件
	private String startCreateDate;
	private String endCreateDate;
	


	public String getStartCreateDate() {
		return startCreateDate;
	}

	public void setStartCreateDate(String startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public String getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public String getWbName() {
		return wbName;
	}

	public void setWbName(String wbName) {
		this.wbName = wbName;
	}

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
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

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public WB getWb() {
		return wb;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAppointName() {
		return appointName;
	}

	public void setAppointName(String appointName) {
		this.appointName = appointName;
	}

	public User getAppoint() {
		return appoint;
	}

	public void setAppoint(User appoint) {
		this.appoint = appoint;
	}

	public void setWb(WB wb) {
		this.wb = wb;
	}

	public Set<Reply> getReplySet() {
		return replySet;
	}
	public void setReplySet(Set<Reply> replySet) {
		this.replySet = replySet;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getTitle() {
		return title;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}


	public Classify getClassify() {
		return classify;
	}

	public void setClassify(Classify classify) {
		this.classify = classify;
	}

	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	

}
