package xymz.model;

public class ClassifyCount implements Comparable{

	private String classifyText;
	private Long count;
	public String getClassifyText() {
		return classifyText;
	}
	public void setClassifyText(String classifyText) {
		this.classifyText = classifyText;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	@Override
	public int compareTo(Object o) {
		
		return 0;
	}
	
}
