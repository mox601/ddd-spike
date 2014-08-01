package org.mox.spikes.domain.repository.events;

import org.joda.time.DateTime;
import org.mox.spikes.domain.apis.DomainEvent;
import org.mox.spikes.domain.customisation.CarId;
import org.mox.spikes.domain.events.ColorChanged;
import org.mox.spikes.domain.events.TireSizeChanged;
import org.mox.spikes.domain.registration.PlateId;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class InMemoryEventStoreTestCase {

    private InMemoryEventStore inMemoryEventStore;

    @BeforeClass
    public void setUp() throws Exception {

        inMemoryEventStore = new InMemoryEventStore();

    }

    @AfterClass
    public void tearDown() throws Exception {

        inMemoryEventStore.close();

    }

    @Test
    public void shouldStore() {

        final DomainEvent first = new SomethingHappened();
        final DomainEvent second = new SomethingHappened();
        this.inMemoryEventStore.append(first);
        this.inMemoryEventStore.append(second);

        assertEquals(this.inMemoryEventStore.countStoredEvents(), 2);
    }

    @Test
    public void testAppend() throws Exception {

        final CarId carId = new CarId("a-car-id");
        final ColorChanged first = new ColorChanged(carId, "blue", new DateTime(1L));
        final TireSizeChanged second = new TireSizeChanged(
                new PlateId(carId.getId()), 19,
                new DateTime(2L));

        this.inMemoryEventStore.append(first);
        final StoredEvent latest = this.inMemoryEventStore.append(second);

        final List<StoredEvent> eventsBetween = this.inMemoryEventStore
                .allStoredEventsBetween(1L, 2L);
        assertEquals(eventsBetween.size(), 2);

        final List<StoredEvent> storedEvents = this.inMemoryEventStore
                .allStoredEventsSince(latest.getEventId());

        assertEquals(storedEvents.size(), 0);

    }

    public class SomethingHappened implements DomainEvent {

        private final int eventVersion;

        private final DateTime occurredOn;

        public SomethingHappened() {

            this.eventVersion = 1;
            this.occurredOn = new DateTime();

        }

        @Override
        public int eventVersion() {

            return eventVersion;
        }

        @Override
        public DateTime occurredOn() {

            return occurredOn;
        }

        @Override
        public boolean equals(Object o) {

            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            SomethingHappened that = (SomethingHappened) o;

            if (eventVersion != that.eventVersion) {
                return false;
            }
            if (occurredOn != null ? !occurredOn.equals(
                    that.occurredOn) : that.occurredOn != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {

            int result = eventVersion;
            result = 31 * result + (occurredOn != null ? occurredOn.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {

            return "SomethingHappened{" +
                    "eventVersion=" + eventVersion +
                    ", occurredOn=" + occurredOn +
                    '}';
        }
    }
}
