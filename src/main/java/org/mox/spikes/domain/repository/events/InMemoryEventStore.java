package org.mox.spikes.domain.repository.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import org.mox.spikes.domain.apis.DomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class InMemoryEventStore implements EventStore, CleanableStore {

    private List<StoredEvent> storedEvents;

    @Inject
    public InMemoryEventStore() {

        this(new ArrayList<StoredEvent>());
    }

    public InMemoryEventStore(final List<StoredEvent> storedEvents) {

        this.storedEvents = storedEvents;
    }

    @Override
    public StoredEvent append(final DomainEvent aDomainEvent) {

        String eventSerialization = null;
        try {
            eventSerialization = EventSerializer.instance().serialize(aDomainEvent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        final StoredEvent storedEvent =
                new StoredEvent(this.storedEvents.size() + 1,
                                aDomainEvent.getClass().getName(),
                                aDomainEvent.occurredOn(),
                                eventSerialization);

        this.storedEvents.add(storedEvent);

        return storedEvent;

    }

    @Override
    public List<StoredEvent> allStoredEventsBetween(final long aLowStoredEventId,
                                                    final long aHighStoredEventId) {

        final List<StoredEvent> events = new ArrayList<StoredEvent>();

        for (StoredEvent storedEvent : this.storedEvents) {
            if (storedEvent.getEventId() >= aLowStoredEventId &&
                    storedEvent.getEventId() <= aHighStoredEventId) {
                events.add(storedEvent);
            }
        }

        return events;
    }

    @Override
    public List<StoredEvent> allStoredEventsSince(final long aStoredEventId) {

        final List<StoredEvent> events = new ArrayList<StoredEvent>();

        for (StoredEvent storedEvent : this.storedEvents) {
            if (storedEvent.getEventId() > aStoredEventId) {
                events.add(storedEvent);
            }
        }

        return events;
    }

    @Override
    public void close() {

        this.clean();
    }

    @Override
    public long countStoredEvents() {

        return this.storedEvents.size();
    }

    @Override
    public void clean() {

        this.storedEvents = new ArrayList<StoredEvent>();
    }
}
