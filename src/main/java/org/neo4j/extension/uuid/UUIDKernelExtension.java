package org.neo4j.extension.uuid;

import java.util.Collections;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.index.AutoIndexer;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.kernel.lifecycle.LifecycleAdapter;

public class UUIDKernelExtension extends LifecycleAdapter{
  
  private final GraphDatabaseService gdb;

  public UUIDKernelExtension(GraphDatabaseService gdb){
    this.gdb = gdb;
  }
  
  @Override
  public void start() throws Throwable{
    System.out.println("UUIDKernelExtension.start begin");

    IndexManager indexManager = this.gdb.index();
    setupUUIDIndexing(indexManager.getNodeAutoIndexer());
    setupUUIDIndexing(indexManager.getRelationshipAutoIndexer());
    this.gdb.registerTransactionEventHandler(new UUIDTransactionEventHandler<String>());

    System.out.println("UUIDKernelExtension.start end");
    
  }
  
  void setupUUIDIndexing(AutoIndexer<? extends PropertyContainer> autoIndexer) {
    autoIndexer.startAutoIndexingProperty(UUIDTransactionEventHandler.UUID_PROPERTY_NAME);
    autoIndexer.setEnabled(true);
    System.out.println("UUIDKernelExtension.setupUUIDIndexing");
}

}
