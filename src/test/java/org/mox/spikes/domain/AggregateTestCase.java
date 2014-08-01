package org.mox.spikes.domain;

import org.joda.time.DateTime;
import org.mox.spikes.domain.apis.DomainEvent;
import org.mox.spikes.domain.infrastructure.DomainEventPublisher;
import org.mox.spikes.domain.infrastructure.DomainEventSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertEquals;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class AggregateTestCase {

    private DomainEventPublisher domainEventPublisher;

    private SomethingHappenedSubscriber somethingHappenedSubscriber;

    @BeforeMethod
    public void setUp() throws Exception {
        //get publisher and register subscriber
        this.domainEventPublisher = DomainEventPublisher.getInstance();
        this.somethingHappenedSubscriber = new SomethingHappenedSubscriber();
        this.domainEventPublisher.subscribe(this.somethingHappenedSubscriber);
    }

    @Test
    public void givenSomeChangesWhenPublishingThenSubscriberReceives() throws Exception {

        final SomeConcreteAggregate anAggregate = new SomeConcreteAggregate();
        anAggregate.doSomethingBusinessLogicIntensive();
        assertEquals(anAggregate.events.size(), 1);
        assertEquals(this.somethingHappenedSubscriber.events.size(), 0);

        //only _after_ calling .publishChanges() they are published
        anAggregate.publishChanges();

        assertEquals(anAggregate.events.size(), 0);
        assertEquals(this.somethingHappenedSubscriber.events.size(), 1);
    }

    private static class SomeConcreteAggregate extends Aggregate {

        private static final Logger LOGGER = LoggerFactory.getLogger(SomeConcreteAggregate.class);

        private SomeConcreteAggregate() {

            super();
        }

        public void doSomethingBusinessLogicIntensive() {
            // do it
            // and then save event using super method
            super.saveEvent(new SomethingHappened(UUID.randomUUID().toString(), new DateTime()));
        }
    }

    public static class SomethingHappened implements DomainEvent {

        final String value;

        private final int eventVersion;

        private final DateTime occurredOn;

        public SomethingHappened(final String value, final DateTime occurredOn) {

            this.eventVersion = 1;
            this.value = value;
            this.occurredOn = occurredOn;
        }

        public String getValue() {

            return value;
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
        public String toString() {

            return "SomethingHappened{" +
                    "value='" + value + '\'' +
                    ", eventVersion=" + eventVersion +
                    ", occurredOn=" + occurredOn +
                    '}';
        }
    }

    private static class SomethingHappenedSubscriber extends InMemoryEvents
            implements DomainEventSubscriber<SomethingHappened> {

        private SomethingHappenedSubscriber() {

            super();

        }

        @Override
        public Class<SomethingHappened> subscribedToEventType() {

            return SomethingHappened.class;
        }

        @Override
        public void handleEvent(final SomethingHappened aDomainEvent) {

            super.events.add(aDomainEvent);
        }
    }

    private static abstract class InMemoryEvents {

        final List<DomainEvent> events;

        public InMemoryEvents() {

            this.events = new ArrayList<DomainEvent>();
        }

    }
}
