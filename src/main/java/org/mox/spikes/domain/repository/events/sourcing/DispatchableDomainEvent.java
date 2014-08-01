package org.mox.spikes.domain.repository.events.sourcing;

import org.mox.spikes.domain.apis.DomainEvent;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class DispatchableDomainEvent {

    private DomainEvent domainEvent;

    private long eventId;

    public DispatchableDomainEvent(long anEventId, DomainEvent aDomainEvent) {

        super();

        this.domainEvent = aDomainEvent;
        this.eventId = anEventId;
    }

    public DomainEvent domainEvent() {

        return this.domainEvent;
    }

    public long eventId() {

        return this.eventId;
    }

}
