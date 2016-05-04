package org.neo4j.extension.uuid;

import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class UUIDTransactionEventHandlerTest extends UUIDTestBase{

  @Test
  public void shouldCreateUUIDToNewNode() {
    GraphDatabaseService graphdb = new GraphDatabaseFactory()
        .newEmbeddedDatabaseBuilder(new java.io.File(TEST_DATA_STORE_DESTINATION)).newGraphDatabase();
    graphdb.registerTransactionEventHandler(new UUIDTransactionEventHandler<String>(true, true));
    super.checkUUIDCreation(graphdb);    
  }

}