package cs.stackexchange.bd;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.logging.Logger;

import org.bson.Document;

public class MongoDBConnector {
	
	static MongoClient mongoClient;
	static MongoDatabase database;
	public static MongoCollection<Document> collection;
	
	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public static void connect() {
		mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		database = mongoClient.getDatabase("DocumentDB");
		collection= database.getCollection("Collection");
	}
	
	public static void disconnect() {
		mongoClient.close();
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

