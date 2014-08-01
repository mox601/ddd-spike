package org.mox.spikes.domain.handlers;

import com.google.inject.Inject;
import org.mox.spikes.domain.apis.DomainEvent;
import org.mox.spikes.domain.infrastructure.DomainEventSubscriber;
import org.mox.spikes.domain.repository.events.EventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class DomainEventHandler implements DomainEventSubscriber<DomainEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainEventHandler.class);

    private final EventStore eventStore;

    @Inject
    public DomainEventHandler(final EventStore eventStore) {

        this.eventStore = eventStore;
    }

    @Override
    public Class<DomainEvent> subscribedToEventType() {

        return DomainEvent.class;
    }

    @Override
    public void handleEvent(final DomainEvent aDomainEvent) {

        this.eventStore.append(aDomainEvent);

    }
}
