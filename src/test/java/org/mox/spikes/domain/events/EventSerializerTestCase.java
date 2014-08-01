package org.mox.spikes.domain.events;

import org.joda.time.DateTime;
import org.mox.spikes.domain.registration.PlateId;
import org.mox.spikes.domain.repository.events.EventSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class EventSerializerTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventSerializerTestCase.class);

    @Test
    public void testSerDe() throws Exception {

        final TireSizeChanged aDomainEvent = new TireSizeChanged(new PlateId("1"), 19,
                                                                 new DateTime());
        final String serializedEvent = EventSerializer.instance().serialize(aDomainEvent);
        LOGGER.info(serializedEvent);
        final TireSizeChanged deserialized = EventSerializer.instance()
                                                            .deserialize(serializedEvent,
                                                                         TireSizeChanged.class);
        assertEquals(deserialized, aDomainEvent);
    }
}
