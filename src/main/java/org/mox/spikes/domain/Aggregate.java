package org.mox.spikes.domain;

import org.mox.spikes.domain.apis.DomainEvent;
import org.mox.spikes.domain.infrastructure.DomainEventPublisher;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO use as common base object of all aggregates?
 * see also how vernon uses EventSourcedRootEntity in iddd
 *
 * pro:
 *
 * contro:
 *
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public abstract class Aggregate {

    protected List<DomainEvent> events;

    protected Aggregate() {

        this(new ArrayList<DomainEvent>());
    }

    protected Aggregate(final List<DomainEvent> events) {

        this.events = events;
    }

    protected void saveEvent(final DomainEvent change) {

        this.events.add(change);
    }

    protected void publishChanges() {

        final DomainEventPublisher publisher = DomainEventPublisher.getInstance();
        //publish and clear
        for (final DomainEvent event: this.events) {
            publisher.publish(event);
        }

        this.events.clear();
    }

}
