package org.neo4j.extension.uuid;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.lifecycle.LifecycleAdapter;

public class UUIDKernelExtension extends LifecycleAdapter{
  
  private final GraphDatabaseService gdb;
  private boolean updateRelationships = false;
  private boolean checkForUuidChanges = false;
  private boolean setupAutoIndexing = false;

  
  public UUIDKernelExtension(GraphDatabaseService gdb, boolean updateRelationships, boolean checkForUuidChanges, boolean setupAutoIndexing){
    this.gdb = gdb;
    this.updateRelationships = updateRelationships;
    this.checkForUuidChanges = checkForUuidChanges;
    this.setupAutoIndexing = setupAutoIndexing;
  }
  
  @Override
  public void start() throws Throwable{
    UUIDBase.start(this.gdb, this.updateRelationships, this.checkForUuidChanges, this.setupAutoIndexing);
  }

}
