package cs.stackexchange.bd;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

public class MongoDBConnector {
	
	static MongoClient mongoClient;
	static MongoDatabase database;
	static MongoCollection<Document> collection;
	
	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public static void connect() {
		mongoClient = new MongoClient();
		database = mongoClient.getDatabase("DocumentDB");
		collection = database.getCollection("Collection");
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
	
	
	public static void main(String[] args) throws IOException {
		connect();
		System.out.println(database.getName());
		System.out.println(collection.getNamespace());
		logger.log(Level.INFO,
				"MongoDB started \nHit enter to stop it...");
		System.in.read();
		disconnect();
		
	}
	
}

