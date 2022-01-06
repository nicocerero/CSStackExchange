package cs.stackexchange.data;

import java.util.Date;

public class Vote {
	
	private int id;
	private int postId;
	private int type;
	private Date creationDate;
	
	public Vote(int id, int postId, int type, Date creationDate) {
		super();
		this.id = id;
		this.postId = postId;
		this.type = type;
		this.creationDate = creationDate;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
}
