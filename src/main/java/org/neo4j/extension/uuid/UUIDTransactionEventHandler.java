package org.neo4j.extension.uuid;

import java.util.UUID;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.event.PropertyEntry;
import org.neo4j.graphdb.event.TransactionData;
import org.neo4j.graphdb.event.TransactionEventHandler;

/**
 * a {@see TransactionEventHandler} that
 * <ul>
 * <li>generates UUID properties for each new node and relationship</li>
 * <li>rejects any modification to pre-existing uuids</li>
 * </ul>
 */
public class UUIDTransactionEventHandler<T> implements
    TransactionEventHandler<T> {

  public static final String UUID_PROPERTY_NAME = "uuid";

  @Override
  public T beforeCommit(TransactionData data) throws Exception {
    checkForUuidChanges(data.removedNodeProperties(), "remove");
    checkForUuidChanges(data.assignedNodeProperties(), "assign");
    checkForUuidChanges(data.removedRelationshipProperties(), "remove");
    checkForUuidChanges(data.assignedRelationshipProperties(), "assign");
    Iterable<Node> createdNodes = data.createdNodes();
    populateUuidsFor(createdNodes);
    populateUuidsFor(data.createdRelationships());
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
    for (PropertyContainer propertyContainer : propertyContainers) {
      if (!propertyContainer.hasProperty(UUID_PROPERTY_NAME)) {
        final UUID uuid = UUID.randomUUID();
        final StringBuilder sb = new StringBuilder();
        sb.append(Long.toHexString(uuid.getMostSignificantBits())).append(
            Long.toHexString(uuid.getLeastSignificantBits()));
        propertyContainer.setProperty(UUID_PROPERTY_NAME, sb.toString());
      }
    }
  }

  private void checkForUuidChanges(Iterable<? extends PropertyEntry<? extends PropertyContainer>> changeList, 
                                   String action) {
    for (PropertyEntry<? extends PropertyContainer> removedProperty : changeList) {
      if (removedProperty.key().equals(UUID_PROPERTY_NAME)) {
        throw new IllegalStateException("you are not allowed to " + action
            + " " + UUID_PROPERTY_NAME + " properties");
      }
    }
  }
}
