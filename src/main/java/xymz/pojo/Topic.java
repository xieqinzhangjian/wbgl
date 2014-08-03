package xymz.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

//主题
public class Topic implements Serializable {

	/** 普通帖 */
	public static final int TYPE_NORMAL = 0;

	/** 精华帖 */
	public static final int TYPE_BEST = 1;

	/** 置顶帖 */
	public static final int TYPE_TOP = 2;
	private int type;// 类型
	private Integer id;
	private String title; // 标题
	private String content;// 内容
	private User author;// 作者
	private String authorName; // 万一删除了用户,还有个名字
	private User lastUpdateUser;// 作者
	private Date postTime;// 发表时间
	private Reply lastReply;// 最后回复
	private Date lastUpdateTime;// 最后更新时间（主题发表时间或最后回复的时间）
	private Set<Reply> replySet = new TreeSet<Reply>();
	private String ipAddr;// 发表文章时所用的IP地址
	private int replyCount = 0;// 回复数量

	private KnowledgeMenu kMenu;

	// 不参与映射

	private Integer knowledgeMenuId;
	
	private String conditionStr;
	private int conditionType;
	
	
	

	public String getConditionStr() {
		return conditionStr;
	}

	public void setConditionStr(String conditionStr) {
		this.conditionStr = conditionStr;
	}


	public int getConditionType() {
		return conditionType;
	}

	public void setConditionType(int conditionType) {
		this.conditionType = conditionType;
	}


	// 分页信息
	private int currentPage = 0; // 当前页
	private int pageSize = 2; // 每页显示多少条记录

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public KnowledgeMenu getkMenu() {
		return kMenu;
	}

	public void setkMenu(KnowledgeMenu kMenu) {
		this.kMenu = kMenu;
	}

	public Integer getKnowledgeMenuId() {
		return knowledgeMenuId;
	}

	public void setKnowledgeMenuId(Integer knowledgeMenuId) {
		this.knowledgeMenuId = knowledgeMenuId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public Reply getLastReply() {
		return lastReply;
	}

	public void setLastReply(Reply lastReply) {
		this.lastReply = lastReply;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Set<Reply> getReplySet() {
		return replySet;
	}

	public void setReplySet(Set<Reply> replySet) {
		this.replySet = replySet;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public static int getTypeNormal() {
		return TYPE_NORMAL;
	}

	public static int getTypeBest() {
		return TYPE_BEST;
	}

	public static int getTypeTop() {
		return TYPE_TOP;
	}

	public User getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(User lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

}
