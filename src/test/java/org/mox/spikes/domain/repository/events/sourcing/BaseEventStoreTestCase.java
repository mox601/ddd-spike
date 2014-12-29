package org.mox.spikes.domain.repository.events.sourcing;

import org.joda.time.DateTime;
import org.mox.spikes.domain.apis.DomainEvent;
import org.mox.spikes.domain.customisation.CarId;
import org.mox.spikes.domain.events.ColorChanged;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertEquals;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public abstract class BaseEventStoreTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseEventStoreTestCase.class);

    org.mox.spikes.domain.repository.events.sourcing.EventStore eventStore;

    @Test
    public void shouldAppend() {

        final EventStreamId aStartingId = new EventStreamId("name");
        final List<DomainEvent> anEvents = new ArrayList<DomainEvent>(1);
        final ColorChanged blue = new ColorChanged(
                new CarId(UUID.randomUUID().toString()), "blue",
                new DateTime());

        anEvents.add(blue);

        this.eventStore.appendWith(aStartingId, anEvents);
        final EventStream eventStream = this.eventStore
                .fullEventStreamFor(aStartingId);
        assertEquals(eventStream.events().get(0), anEvents.get(0));
    }


}
