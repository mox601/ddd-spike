package org.mox.spikes.domain.infrastructure;

import org.mox.spikes.domain.apis.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class DomainEventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainEventPublisher.class);

    private static final ThreadLocal<Boolean> PUBLISHING = new ThreadLocal<Boolean>() {

        @Override
        protected Boolean initialValue() {

            return Boolean.FALSE;
        }
    };

    private static final ThreadLocal<List> SUBSCRIBERS = new ThreadLocal<List>();

    public static DomainEventPublisher getInstance() {

        return new DomainEventPublisher();
    }

    private DomainEventPublisher() {

        super();
    }

    public <T> void publish(final T aDomainEvent) {

        if (PUBLISHING.get()) {
            return;
        }

        try {

            PUBLISHING.set(Boolean.TRUE);

            final List<DomainEventSubscriber<T>> registeredSubscribers = SUBSCRIBERS.get();

            if (registeredSubscribers != null) {

                final Class<?> eventType = aDomainEvent.getClass();

                for (final DomainEventSubscriber<T> subscriber : registeredSubscribers) {

                    final Class<?> subscribedTo = subscriber.subscribedToEventType();

                    if (subscribedTo == eventType ||
                            subscribedTo == DomainEvent.class) {
                        subscriber.handleEvent(aDomainEvent);
                    }
                }
            }

        } finally {
            PUBLISHING.set(Boolean.FALSE);
        }
    }

    public <T> void subscribe(final DomainEventSubscriber<T> aSubscriber) {

        if (PUBLISHING.get()) {
            return;
        }

        List<DomainEventSubscriber<T>> registeredSubscribers = SUBSCRIBERS.get();

        if (registeredSubscribers == null) {
            registeredSubscribers = new ArrayList<DomainEventSubscriber<T>>();
            SUBSCRIBERS.set(registeredSubscribers);
        }
        registeredSubscribers.add(aSubscriber);
    }

    public DomainEventPublisher reset() {

        if (!PUBLISHING.get()) {
            SUBSCRIBERS.set(null);
        }

        return this;
    }

    public static <T> void subscribeToPublisherInstance(
            final DomainEventSubscriber<T> customisationEventsHandler) {

        final DomainEventPublisher publisher = DomainEventPublisher.getInstance();
        publisher.subscribe(customisationEventsHandler);

    }
}
