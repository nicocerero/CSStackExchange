package cs.stackexchange.data;

import org.bson.Document;

public class Tag {

	private String tagName;
	private String description;

	public Tag(String tagName, String description) {
		super();
		this.tagName = tagName;
		this.description = description;
	}

	public Tag(Document d) {
		super();
		this.tagName = (String) d.get("tagName");
		this.description = (String) d.get("description");
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getDescription() {
		return description;
	}

	public void getDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return  tagName;
	}
	
	
}
