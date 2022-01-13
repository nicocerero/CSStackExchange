package cs.stackexchange.data;

import java.util.ArrayList;
import java.util.Date;

import java.util.Iterator;
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
	private ArrayList<Tag> tags;
	private ArrayList<Comment> comments;
	private int parentId;

	public Post(int id, int postTypeId, int acceptedAnswerId, Date creationDate, int score, int viewCount, String title,
			String body, int ownerUserId, ArrayList<Tag> tags, ArrayList<Comment> comments, int parentId) {
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
		this.parentId = (int) d.get("parentId");
		// Now, handle the iterators for Tags and Comments.
		this.tags = new ArrayList<>();
		@SuppressWarnings("unchecked")
		Iterator<Document> it_tag = (Iterator<Document>) d.get("tags");
		while (it_tag.hasNext()) {
			Tag t = new Tag(it_tag.next());
			if (t != null) {
				this.tags.add(t);
			}
		}
		this.comments = new ArrayList<>();
		@SuppressWarnings("unchecked")
		Iterator<Document> it_com = (Iterator<Document>) d.get("comments");
		while (it_com.hasNext()) {
			Comment c = new Comment(it_com.next());
			if (c != null) {
				this.comments.add(c);
			}
		}
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

	public ArrayList<Tag> getTags() {
		return tags;
	}

	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
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
