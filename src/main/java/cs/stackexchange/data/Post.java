package cs.stackexchange.data;

import java.util.Date;

import org.bson.Document;

public class Post {

	private int id;
	private int postTypeId;
	private int acceptedAnswerId;
	private Date creationDate;
	private int score;
	private int viewCount;
	private String title;
	private String body;
	private int ownerUserId;
	private Tag tags[];
	private Comment comments[];
	private int parentId;

	public Post(int id, int postTypeId, int acceptedAnswerId, Date creationDate, int score, int viewCount, String title,
			String body, int ownerUserId, Tag[] tags, Comment[] comments, int parentId) {
		super();
		this.id = id;
		this.postTypeId = postTypeId;
		this.acceptedAnswerId = acceptedAnswerId;
		this.creationDate = creationDate;
		this.score = score;
		this.viewCount = viewCount;
		this.title = title;
		this.body = body;
		this.ownerUserId = ownerUserId;
		this.tags = tags;
		this.comments = comments;
		this.parentId = parentId;
	}

	public Post() {
		super();
	}

	public Post(Document d) {
		super();
		this.id = (int) d.get("id");
		this.postTypeId = (int) d.get("type");
		this.acceptedAnswerId = (int) d.get("acceptedAnswerId");
		this.creationDate = (Date) d.get("creationDate");
		this.score = (int) d.get("score");
		this.viewCount = (int) d.get("viewCount");
		this.title = (String) d.get("title");
		this.body = (String) d.get("body");
		this.ownerUserId = (int) d.get("ownerUserId");
		this.tags = (Tag[]) d.get("tags");
		this.comments = (Comment[]) d.get("comments");
		this.parentId = (int) d.get("parentId");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return postTypeId;
	}

	public void setType(int type) {
		this.postTypeId = type;
	}

	public int getAcceptedAnswerId() {
		return acceptedAnswerId;
	}

	public void setAcceptedAnswerId(int acceptedAnswerId) {
		this.acceptedAnswerId = acceptedAnswerId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getUserId() {
		return ownerUserId;
	}

	public void setUserId(int ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public Tag[] getTags() {
		return tags;
	}

	public void setTags(Tag[] tags) {
		this.tags = tags;
	}

	public Comment[] getComments() {
		return comments;
	}

	public void setComments(Comment comments[]) {
		this.comments = comments;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "Post (id:" + id + ") [score=" + score + ", title=" + title + "]";
	}

}
