package org.neo4j.extension.uuid

import com.fasterxml.uuid.Generators
import com.fasterxml.uuid.impl.TimeBasedGenerator
import org.neo4j.graphdb.PropertyContainer
import org.neo4j.graphdb.event.PropertyEntry
import org.neo4j.graphdb.event.TransactionData
import org.neo4j.graphdb.event.TransactionEventHandler
import java.util.UUID
import java.lang.{Long=>JLong} 
import scala.collection.JavaConverters._
import org.neo4j.graphdb.Node

/**
 * a {@see TransactionEventHandler} that
 * <ul>
 *     <li>generates UUID properties for each new node and relationship</li>
 *     <li>rejects any modification to pre-existing uuids</li>
 * </ul>
 */
class UUIDTransactionEventHandler[T] extends TransactionEventHandler[T]{

  import UUIDTransactionEventHandler._
  
  val uuidGenerator: TimeBasedGenerator = Generators.timeBasedGenerator();

  override def beforeCommit(data: TransactionData): T = {

    checkForUuidChanges(data.removedNodeProperties().asScala, "remove");
    checkForUuidChanges(data.assignedNodeProperties().asScala, "assign");
    checkForUuidChanges(data.removedRelationshipProperties().asScala, "remove");
    checkForUuidChanges(data.assignedRelationshipProperties().asScala, "assign");

    populateUuidsFor(data.createdNodes().asScala);
    populateUuidsFor(data.createdRelationships().asScala);
    ???
  }

  override def afterCommit(data: TransactionData, state: T) = ???

  override def afterRollback(data: TransactionData, state: T) = ???

  /**
   * @param propertyContainers set UUID property for a iterable on nodes or relationships
   */
  def populateUuidsFor(propertyContainers: Iterable[_ with PropertyContainer] ) {
    propertyContainers.foreach(propertyContainer => {
        if (!propertyContainer.hasProperty(UUID_PROPERTY_NAME)) {

            val uuid: UUID = uuidGenerator.generate();
            val sb: StringBuilder = new StringBuilder();
            sb.append(JLong.toHexString(uuid.getMostSignificantBits())).append(JLong.toHexString(uuid.getLeastSignificantBits()));

            propertyContainer.setProperty(UUID_PROPERTY_NAME, sb.toString());
        }
    })
  }

  def checkForUuidChanges(changeList: Iterable[_ with PropertyEntry[_ with PropertyContainer]], action: String) {
      changeList.foreach(removedProperty =>  {
          if (removedProperty.key().equals(UUID_PROPERTY_NAME)) {
              throw new IllegalStateException("you are not allowed to " + action + " " + UUID_PROPERTY_NAME + " properties");
          }
      })
  }
}

object UUIDTransactionEventHandler{
  val UUID_PROPERTY_NAME = "uuid"
}