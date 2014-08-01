package org.mox.spikes.domain.repository.events.sourcing;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class EventStoreProvider {

    private EventStore eventStore;

    public static EventStoreProvider instance() {

        return new EventStoreProvider();
    }

    public EventStore eventStore() {

        return this.eventStore;
    }

    protected EventStoreProvider() {

        super();

        this.eventStore = new InMemoryEventStore();

    }
}
