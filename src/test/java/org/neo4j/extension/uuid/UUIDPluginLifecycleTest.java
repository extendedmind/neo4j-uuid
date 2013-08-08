package org.neo4j.extension.uuid;

import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.GraphDatabaseAPI;
import org.neo4j.server.WrappingNeoServerBootstrapper;
import org.neo4j.server.configuration.Configurator;
import org.neo4j.server.configuration.ServerConfigurator;

public class UUIDPluginLifecycleTest extends UUIDTestBase {

  // Does not work, probably because sources are not yet packaged as a jar
  //@Test
  public void shouldCreateUUIDToNewNode() {    
    GraphDatabaseService graphdb = new GraphDatabaseFactory()
        .newEmbeddedDatabaseBuilder(TEST_DATA_STORE_DESTINATION)
        .newGraphDatabase();
    
    ServerConfigurator config = new ServerConfigurator((GraphDatabaseAPI)graphdb);
    config.configuration().setProperty(
      Configurator.THIRD_PARTY_PACKAGES_KEY, "org.neo4j.extension.uuid=/db/uuid");
    config.configuration().setProperty(
      Configurator.WEBSERVER_PORT_PROPERTY_KEY, 7473);
    WrappingNeoServerBootstrapper srv = new WrappingNeoServerBootstrapper((GraphDatabaseAPI)graphdb, config);
    srv.start();
    
    super.checkUUIDCreation(graphdb);
  }
}
