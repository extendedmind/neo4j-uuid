package org.neo4j.extension.uuid;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.lifecycle.LifecycleAdapter;

public class UUIDKernelExtension extends LifecycleAdapter{
  
  private final GraphDatabaseService gdb;
  private boolean checkForUuidChanges = false;
  
  public UUIDKernelExtension(GraphDatabaseService gdb, boolean checkForUuidChanges){
    this.gdb = gdb;
    this.checkForUuidChanges = checkForUuidChanges;
  }
  
  @Override
  public void start() throws Throwable{
    UUIDBase.start(this.gdb, this.checkForUuidChanges);
  }

}
