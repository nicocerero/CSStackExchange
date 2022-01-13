package cs.stackexchange.data;

import org.bson.Document;

public class Tag {

	private int id;
	private String tagName;
	private String description;

	public Tag(int id, String tagName, String description) {
		super();
		this.id = id;
		this.tagName = tagName;
		this.description = description;
	}

	public Tag(Document d) {
		super();
		this.id = (int) d.get("id");
		this.tagName = (String) d.get("tagName");
		this.description = (String) d.get("description");
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

	public String getDescription() {
		return description;
	}

	public void getDescription(String description) {
		this.description = description;
	}

}
