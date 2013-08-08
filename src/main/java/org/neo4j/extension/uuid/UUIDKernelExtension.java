package org.neo4j.extension.uuid;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.lifecycle.LifecycleAdapter;

public class UUIDKernelExtension extends LifecycleAdapter{
  
  private final GraphDatabaseService gdb;

  public UUIDKernelExtension(GraphDatabaseService gdb){
    this.gdb = gdb;
  }
  
  @Override
  public void start() throws Throwable{
    UUIDBase.start(this.gdb);
  }

}
