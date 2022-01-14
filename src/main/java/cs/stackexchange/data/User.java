package cs.stackexchange.data;

import org.neo4j.driver.Record;

public class User {

	private int id;
	private int reputation;
	private String username;
	private String aboutMe;
	private String creationDate;

	public User(int id, int reputation, String username, String aboutMe, String creationDate) {
		super();
		this.id = id;
		this.reputation = reputation;
		this.username = username;
		this.aboutMe = aboutMe;
		this.creationDate = creationDate;
	}

	public User(Record r) {
		super();
		this.id = Integer.parseInt(r.get("n.id").asObject().toString());
		this.reputation = Integer.parseInt(r.get("n.reputation").asObject().toString());
		this.username = r.get("n.username").toString();
		this.aboutMe = r.get("n.aboutMe").toString();
		this.creationDate = r.get("n.creationDate").toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return username + " | " + reputation;
	}

}
