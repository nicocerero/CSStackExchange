package cs.stackexchange.bd;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class MongoDBConnector {
	
	static MongoClient mongoClient;
	static MongoDatabase database;
	static MongoCollection<Document> collection;
	
	public static void connect() {
		mongoClient = new MongoClient();
		database = mongoClient.getDatabase("DocumentDB");
		collection = database.getCollection("Collection");
	}
	
	public static Block<Document> printBlock = new Block<Document>() {
	    @Override
	    public void apply(final Document document) {
	        System.out.println(document.toJson());
	    }
	};
	
	public static void main(String[] args) {
		connect();
		System.out.println(database.getName());
		System.out.println(collection.getNamespace());
		mongoClient.close();
	}
	
}

