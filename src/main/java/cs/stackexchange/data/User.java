package cs.stackexchange.data;

public class User {
	
	private int id;
	private int accountId;
	private int reputation;
	private String username;
	private String aboutMe;
	private int views;
	private int upVotes;
	private int downVotes;
	
	public User(int id, int accountId, int reputation, String username, String aboutMe, int views, int upVotes,
			int downVotes) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.reputation = reputation;
		this.username = username;
		this.aboutMe = aboutMe;
		this.views = views;
		this.upVotes = upVotes;
		this.downVotes = downVotes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getReputation() {
		return reputation;
	}

	public void setReputation(int reputation) {
		this.reputation = reputation;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getUpVotes() {
		return upVotes;
	}

	public void setUpVotes(int upVotes) {
		this.upVotes = upVotes;
	}

	public int getDownVotes() {
		return downVotes;
	}

	public void setDownVotes(int downVotes) {
		this.downVotes = downVotes;
	}
	
	
}
