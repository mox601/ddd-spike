package org.mox.spikes.domain.repository.events.sourcing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * TODO complete
 *  * TODO add implementation with chronicle
 *
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ChronicleEventStoreTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChronicleEventStoreTestCase.class);

    @BeforeClass
    public void setUp() throws Exception {

        //        eventStore = new ChronicleEventStore(new IndexedChronicle(""));

    }

    @AfterClass
    public void tearDown() throws Exception {

        //        eventStore.close();

    }
}
