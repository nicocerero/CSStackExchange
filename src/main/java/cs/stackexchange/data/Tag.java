package cs.stackexchange.data;

public class Tag {
	
	private int id;
	private String tagName;
	private int count;
	
	public Tag(int id, String tagName, int count) {
		super();
		this.id = id;
		this.tagName = tagName;
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
