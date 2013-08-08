package org.neo4j.extension.uuid;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.configuration.Configuration;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.server.plugins.Injectable;
import org.neo4j.server.plugins.PluginLifecycle;

/**
 * implementation of {@see PluginLifecycle} that sets up autoindexing for UUID
 * property and registers the {@see UUIDTransactionEventHandler}.
 */
public class UUIDPluginLifecycle implements PluginLifecycle {

  @Override
  public Collection<Injectable<?>> start(GraphDatabaseService graphDatabaseService, Configuration config) {
    UUIDBase.start(graphDatabaseService);
    return Collections.emptySet();
  }

  @Override
  public void stop() {
    // intentionally empty
  }
}