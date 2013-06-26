package org.neo4j.extension.uuid

import org.apache.commons.configuration.Configuration;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.index.AutoIndexer;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.server.plugins.Injectable;
import org.neo4j.server.plugins.PluginLifecycle;

import java.util.Collection;
import java.util.Collections;

class UUIDPluginLifecycle extends PluginLifecycle {

    override def start(graphDatabaseService: GraphDatabaseService, config: Configuration): Collection[Injectable[_]] = {
      val indexManager = graphDatabaseService.index
      setupUUIDIndexing(indexManager.getNodeAutoIndexer());
      setupUUIDIndexing(indexManager.getRelationshipAutoIndexer());
      graphDatabaseService.registerTransactionEventHandler(new UUIDTransactionEventHandler[String]());
      return Collections.emptySet();
    }

    override def stop() = {
        // intentionally empty
    }

    def setupUUIDIndexing(autoIndexer: AutoIndexer[_ with PropertyContainer] ) {
        autoIndexer.startAutoIndexingProperty(UUIDTransactionEventHandler.UUID_PROPERTY_NAME);
        autoIndexer.setEnabled(true);
    }
}