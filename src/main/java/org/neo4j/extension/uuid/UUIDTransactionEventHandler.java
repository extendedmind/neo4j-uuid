package org.neo4j.extension.uuid;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.event.PropertyEntry;
import org.neo4j.graphdb.event.TransactionData;
import org.neo4j.graphdb.event.TransactionEventHandler;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * a {@see TransactionEventHandler} that
 * <ul>
 *     <li>generates UUID properties for each new node and relationship</li>
 *     <li>rejects any modification to pre-existing uuids</li>
 * </ul>
 */
public class UUIDTransactionEventHandler<T> implements TransactionEventHandler<T> {

    public static final String UUID_PROPERTY_NAME = "uuid";

    private Logger logger = Logger.getLogger("org.neo4j"); 
    
    public UUIDTransactionEventHandler(){
      System.out.println("UUIDTransactionEventHandler.UUIDTransactionEventHandler");
    }
    
    @Override
    public T beforeCommit(TransactionData data) throws Exception {
      System.out.println("UUIDTransactionEventHandler.beforeCommit start");
      checkForUuidChanges(data.removedNodeProperties(), "remove");
      checkForUuidChanges(data.assignedNodeProperties(), "assign");
      checkForUuidChanges(data.removedRelationshipProperties(), "remove");
      checkForUuidChanges(data.assignedRelationshipProperties(), "assign");
      Iterable<Node> createdNodes = data.createdNodes();      
      for (Node node : createdNodes){
        System.out.println("NODE FOUND");        
      }
      populateUuidsFor(createdNodes);
      populateUuidsFor(data.createdRelationships());
      System.out.println("UUIDTransactionEventHandler.beforeCommit end");
      
      return null;
    }

    @Override
    public void afterCommit(TransactionData data, java.lang.Object state) {
    }

    @Override
    public void afterRollback(TransactionData data, java.lang.Object state) {
    }

    /**
     * @param propertyContainers set UUID property for a iterable on nodes or relationships
     */
    private void populateUuidsFor(Iterable<? extends PropertyContainer> propertyContainers) {
      	System.out.println("UUIDTransactionEventHandler.populateUuidsFor");

        for (PropertyContainer propertyContainer : propertyContainers) {
            System.out.println("UUIDTransactionEventHandler.populateUuidsFor looping");

            if (!propertyContainer.hasProperty(UUID_PROPERTY_NAME)) {
                System.out.println("UUIDTransactionEventHandler.populateUuidsFor creating UUID");

                final UUID uuid = UUID.randomUUID();
                final StringBuilder sb = new StringBuilder();
                sb.append(Long.toHexString(uuid.getMostSignificantBits())).append(Long.toHexString(uuid.getLeastSignificantBits()));

                propertyContainer.setProperty(UUID_PROPERTY_NAME, sb.toString());
            }
        }
    }

    private void checkForUuidChanges(Iterable<? extends PropertyEntry<? extends PropertyContainer>> changeList, String action) {
    	System.out.println("UUIDTransactionEventHandler.checkForUuidChanges");

    	for (PropertyEntry<? extends PropertyContainer> removedProperty : changeList) {
            if (removedProperty.key().equals(UUID_PROPERTY_NAME)) {
                throw new IllegalStateException("you are not allowed to " + action + " " + UUID_PROPERTY_NAME + " properties");
            }
        }
    }

}
