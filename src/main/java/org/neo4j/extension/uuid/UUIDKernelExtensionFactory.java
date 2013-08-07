package org.neo4j.extension.uuid;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.extension.KernelExtensionFactory;
import org.neo4j.kernel.lifecycle.Lifecycle;

public class UUIDKernelExtensionFactory extends KernelExtensionFactory<UUIDKernelExtensionFactory.Dependencies>
{
    public interface Dependencies{
        GraphDatabaseService getDatabase();
    }

    public UUIDKernelExtensionFactory(){
        super( "uuid" );
    }

    @Override
    public Lifecycle newKernelExtension( Dependencies dependencies ) throws Throwable {
        return new UUIDKernelExtension(dependencies.getDatabase());
    }
}
