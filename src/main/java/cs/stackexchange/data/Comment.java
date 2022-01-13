package cs.stackexchange.data;

import java.util.Date;

public class Comment {
	
	private int id;
	private int postId;
	private int score;
	private String text;
	private Date creationDate;
	private int userId;
	
	public Comment(int id, int postId, int score, String text, Date creationDate, int userId) {
		super();
		this.id = id;
		this.postId = postId;
		this.score = score;
		this.text = text;
		this.creationDate = creationDate;
		this.userId = userId;
	}

	public Comment() {
		// TODO Auto-generated constructor stub
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
