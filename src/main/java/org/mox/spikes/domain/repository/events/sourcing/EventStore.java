package org.mox.spikes.domain.repository.events.sourcing;

import org.mox.spikes.domain.apis.DomainEvent;

import java.util.List;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public interface EventStore {

    public void appendWith(EventStreamId aStartingIdentity, List<DomainEvent> anEvents);

    public void close();

    public List<DispatchableDomainEvent> eventsSince(long aLastReceivedEvent);

    public EventStream eventStreamSince(EventStreamId anIdentity);

    public EventStream fullEventStreamFor(EventStreamId anIdentity);

    public void purge(); // mainly used for testing

    public void registerEventNotifiable(EventNotifiable anEventNotifiable);

}
