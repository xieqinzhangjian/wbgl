package xymz.pojo;



/**
 * 回复
 * 
 * @author
 */
public class Reply implements Comparable {
	private int id;
	private Problem problem;// 所属的问题
	private Topic topic;//所属主题
	private String content;// 回复内容
	private User user;// 回复的作者
	private String replyDate;//回复的日期
	
	
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Problem getProblem() {
		return problem;
	}
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getReplyDate() {
		return replyDate;
	}
	public void setReplyDate(String replyDate) {
		this.replyDate = replyDate;
	}
	@Override
	public int compareTo(Object o) {
		
		return 0;
	}

	
}
