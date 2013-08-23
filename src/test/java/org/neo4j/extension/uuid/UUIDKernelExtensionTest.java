package org.neo4j.extension.uuid;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.extension.KernelExtensionFactory;

public class UUIDKernelExtensionTest extends UUIDTestBase {

  @Test
  public void shouldCreateUUIDToNewNode() {    
    List<KernelExtensionFactory<?>> extensions = new ArrayList<KernelExtensionFactory<?>>(1); 
    extensions.add(new UUIDKernelExtensionFactory(true));
    GraphDatabaseService graphdb = new GraphDatabaseFactory()
        .addKernelExtensions(extensions)
        .newEmbeddedDatabaseBuilder(TEST_DATA_STORE_DESTINATION)
        .newGraphDatabase();

    super.checkUUIDCreation(graphdb);
  }
}
