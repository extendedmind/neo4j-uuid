package org.neo4j.extension.uuid;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.extension.KernelExtensionFactory;

public class UUIDKernelExtensionTest extends UUIDTestBase {

  @Test
  public void shouldCreateUUIDToNewNode() {    
    List<KernelExtensionFactory<?>> extensions = new ArrayList<KernelExtensionFactory<?>>(1); 
    extensions.add(new UUIDKernelExtensionFactory());
    GraphDatabaseService graphdb = new GraphDatabaseFactory()
        .addKernelExtensions(extensions)
        .newEmbeddedDatabaseBuilder(TEST_DATA_STORE_DESTINATION)
        .newGraphDatabase();

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
