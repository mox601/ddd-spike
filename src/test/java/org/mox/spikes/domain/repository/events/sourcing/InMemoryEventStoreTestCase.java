package org.mox.spikes.domain.repository.events.sourcing;

import org.joda.time.DateTime;
import org.mox.spikes.domain.apis.DomainEvent;
import org.mox.spikes.domain.customisation.CarId;
import org.mox.spikes.domain.events.ColorChanged;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertEquals;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class InMemoryEventStoreTestCase {

    private EventStore inMemoryEventStore;

    @BeforeClass
    public void setUp() throws Exception {

        this.inMemoryEventStore = new InMemoryEventStore();

    }

    @AfterClass
    public void tearDown() throws Exception {

        this.inMemoryEventStore.close();

    }

    @Test
    public void shouldAppend() {

        final EventStreamId aStartingId = new EventStreamId("name");
        final List<DomainEvent> anEvents = new ArrayList<DomainEvent>();
        final ColorChanged blue = new ColorChanged(
                new CarId(UUID.randomUUID().toString()), "blue",
                new DateTime());

        anEvents.add(blue);

        this.inMemoryEventStore.appendWith(aStartingId, anEvents);
        final EventStream eventStream = this.inMemoryEventStore
                .fullEventStreamFor(aStartingId);
        assertEquals(eventStream.events().get(0), anEvents.get(0));
    }

}
