package cs.stackexchange.data;

import org.bson.Document;

public class Comment {

	private int id;
	private int postId;
	private String text;
	private int userId;

	public Comment(int id, int postId, String text, int userId) {
		super();
		this.id = id;
		this.postId = postId;
		this.text = text;
		this.userId = userId;
	}

	public Comment() {
	}

	public Comment(Document d) {
		super();
		this.id = (int) d.get("id");
		this.postId = (int) d.get("postId");
		this.text = (String) d.get("text");
		if(d.get("userId") != null) {
			this.userId = (int) d.get("userId");
		}else {
			this.userId = -1;
		}
		

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
