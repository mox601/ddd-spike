package org.mox.spikes.domain.repository.events.sourcing;

import com.higherfrequencytrading.chronicle.impl.IndexedChronicle;
import org.mox.spikes.domain.apis.DomainEvent;
import org.mox.spikes.domain.repository.events.CleanableStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * TODO implement an event store based on chronicle
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ChronicleEventStore implements EventStore, CleanableStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChronicleEventStore.class);

    private IndexedChronicle indexedChronicle;

    public ChronicleEventStore(final IndexedChronicle indexedChronicle) {

        this.indexedChronicle = indexedChronicle;
    }

    @Override
    public void clean() {

    }

    @Override
    public void appendWith(EventStreamId aStartingIdentity, List<DomainEvent> anEvents) {

    }

    @Override
    public void close() {

    }

    @Override
    public List<DispatchableDomainEvent> eventsSince(long aLastReceivedEvent) {

        throw new UnsupportedOperationException("NIY");
    }

    @Override
    public EventStream eventStreamSince(EventStreamId anIdentity) {

        throw new UnsupportedOperationException("NIY");
    }

    @Override
    public EventStream fullEventStreamFor(EventStreamId anIdentity) {

        throw new UnsupportedOperationException("NIY");
    }

    @Override
    public void purge() {

    }

    @Override
    public void registerEventNotifiable(EventNotifiable anEventNotifiable) {

    }
}
