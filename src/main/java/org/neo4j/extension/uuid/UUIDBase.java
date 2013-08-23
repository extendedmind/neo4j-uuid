package org.neo4j.extension.uuid;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.index.AutoIndexer;
import org.neo4j.graphdb.index.IndexManager;

public abstract class UUIDBase {

  static void start(GraphDatabaseService graphDatabaseService, boolean checkForUuidChanges, boolean setupAutoIndexing){
    IndexManager indexManager = graphDatabaseService.index();
    if (setupAutoIndexing){
      setupUUIDIndexing(indexManager.getNodeAutoIndexer());
      setupUUIDIndexing(indexManager.getRelationshipAutoIndexer());
    }
    graphDatabaseService.registerTransactionEventHandler(new UUIDTransactionEventHandler<String>(checkForUuidChanges));
  }
  
  static void setupUUIDIndexing(AutoIndexer<? extends PropertyContainer> autoIndexer) {
    autoIndexer.startAutoIndexingProperty(UUIDTransactionEventHandler.UUID_PROPERTY_NAME);
    autoIndexer.setEnabled(true);
  }
}
