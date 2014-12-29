package org.mox.spikes.domain.repository.events.sourcing;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */

public class InMemoryEventStoreTestCase extends BaseEventStoreTestCase {

    @BeforeClass
    public void setUp() throws Exception {

        eventStore = new InMemoryEventStore();

    }

    @AfterClass
    public void tearDown() throws Exception {

        eventStore.close();

    }

}
