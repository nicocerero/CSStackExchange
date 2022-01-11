package cs.stackexchange.bd;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

public class Neo4jConnector implements AutoCloseable {

	public static Driver driver;

	public Neo4jConnector(String uri, String user, String password) {
		driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
	}

	public void close() throws Exception {
		driver.close();
	}
	
	public static void main(String[] args) throws Exception {
		Neo4jConnector neo4j = new Neo4jConnector("bolt://localhost:7687", "neo4j", "12345");
		neo4j.close();
	}
}
