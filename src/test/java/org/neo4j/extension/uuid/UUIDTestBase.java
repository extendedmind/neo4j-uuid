package org.neo4j.extension.uuid;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.After;

public class UUIDTestBase {

  protected final String TEST_DATA_STORE_DESTINATION = "target/neo4j-test";

  @After
  public void cleanUp() throws Exception {
    File store = new File(TEST_DATA_STORE_DESTINATION);
    if (store.exists() == true) {
      FileUtils.deleteDirectory(store);
    }
  }
}
