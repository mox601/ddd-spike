package org.mox.spikes.domain.repository.events;

import org.mox.spikes.domain.apis.DomainEvent;

import java.util.List;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public interface EventStore {

    StoredEvent append(final DomainEvent event);

    List<StoredEvent> allStoredEventsBetween(final long aLowStoredEventId,
                                             final long aHighStoredEventId);

    List<StoredEvent> allStoredEventsSince(final long aStoredEventId);

    void close();

    long countStoredEvents();

}
