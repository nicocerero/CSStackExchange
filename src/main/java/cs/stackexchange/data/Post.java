package cs.stackexchange.data;

import java.util.ArrayList;
import java.util.Iterator;

import org.bson.Document;

public class Post {

	private int id;
	private int postTypeId;
	private int acceptedAnswerId;
	private String creationDate;
	private int score;
	private int viewCount;
	private String title;
	private String body;
	private int ownerUserId;
	private ArrayList<Tag> tags;
	private ArrayList<Comment> comments;
	private int parentId;

	public Post(int id, int postTypeId, int acceptedAnswerId, String creationDate, int score, int viewCount,
			String title, String body, int ownerUserId, ArrayList<Tag> tags, ArrayList<Comment> comments,
			int parentId) {
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
		this.postTypeId = (int) d.get("postTypeId");
		if (d.get("acceptedAnswerId") == null) {
			this.acceptedAnswerId = 0;
		} else {
			this.acceptedAnswerId = (int) d.get("acceptedAnswerId");
		}
		this.creationDate = (String) d.get("creationDate");
		this.score = (int) d.get("score");
		if (d.get("viewCount") == null) {
			this.viewCount = 0;
		} else {
			this.viewCount = (int) d.get("viewCount");
		}
		if (d.get("title") == null) {
			this.title = "";
		} else {
			this.title = (String) d.get("title");
		}
		this.body = (String) d.get("body");
		this.ownerUserId = (int) d.get("ownerUserId");
		if (d.get("parentId") == null) {
			this.parentId = 0;
		} else {
			this.parentId = (int) d.get("parentId");
		}
		// Now, handle the iterators for Tags and Comments.
		this.tags = new ArrayList<>();
		Iterator<Document> it_tag = d.getList("tags", Document.class).iterator();
		while (it_tag.hasNext()) {
			Tag t = new Tag(it_tag.next());
			if (t != null) {
				this.tags.add(t);
			}
		}
		this.comments = new ArrayList<>();
		Iterator<Document> it_com = d.getList("comments", Document.class).iterator();
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

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
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
		if (postTypeId == 0) {
			return "(id:" + id + ") | " + score + " | Q: " + title;
		} else {
			return "Answer [score=" + score + "] body=" + body;
		}

	}

}
