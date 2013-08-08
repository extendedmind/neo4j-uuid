package org.neo4j.extension.uuid;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

public class UUIDTestBase {

  protected final String TEST_DATA_STORE_DESTINATION = "target/neo4j-test";

  @After
  public void cleanUp() throws Exception {
    File store = new File(TEST_DATA_STORE_DESTINATION);
    if (store.exists() == true) {
      FileUtils.deleteDirectory(store);
    }
  }
  
  protected void checkUUIDCreation(GraphDatabaseService graphdb){
    Transaction tx = graphdb.beginTx();
    Node node = graphdb.createNode();
    node.setProperty("test", "test");
    long id = node.getId();
    tx.success();
    tx.finish();

    tx = graphdb.beginTx();
    node = graphdb.getNodeById(id);
    node.getProperty("test");
    // New nodes should have a "uuid" property
    node.getProperty("uuid");
    tx.success();
    tx.finish();
   
  }
}
