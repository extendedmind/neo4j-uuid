package org.neo4j.extension.uuid;

import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class UUIDTransactionEventHandlerTest extends UUIDTestBase{

  //@Test uncomment as database should be cleaned after test so that there
  // can be multiple tests
  public void shouldCreateUUIDToNewNode() {
    GraphDatabaseService graphdb = new GraphDatabaseFactory()
        .newEmbeddedDatabaseBuilder(TEST_DATA_STORE_DESTINATION).newGraphDatabase();
    graphdb.registerTransactionEventHandler(new UUIDTransactionEventHandler<String>(true, true));
    super.checkUUIDCreation(graphdb);    
  }

}