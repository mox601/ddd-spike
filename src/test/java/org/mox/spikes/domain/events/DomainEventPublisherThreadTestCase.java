package org.mox.spikes.domain.events;

import org.mox.spikes.domain.apis.DomainEvent;
import org.mox.spikes.domain.customisation.Car;
import org.mox.spikes.domain.customisation.CarId;
import org.mox.spikes.domain.infrastructure.DomainEventPublisher;
import org.mox.spikes.domain.infrastructure.DomainEventSubscriber;
import org.mox.spikes.domain.registration.PlateId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class DomainEventPublisherThreadTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            DomainEventPublisherThreadTestCase.class);

    private DomainEventPublisher publisher;

    private AtomicLong counter = new AtomicLong(0L);

    private List<DomainEvent> events;

    @BeforeClass
    public void setUp() throws Exception {

        final Runnable publisherInitializer = new Runnable() {

            @Override
            public void run() {

                publisher = DomainEventPublisher.getInstance();
                publisher.reset();
            }
        };

        final ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(publisherInitializer);
        executorService.shutdownNow();

        Thread.sleep(200);

        assertNotNull(this.publisher);
    }

    @BeforeMethod
    public void registerHandlerOnThisThread() {

        this.publisher = this.publisher.reset();
        final LocalCustomisationEventsHandler handler =
                new LocalCustomisationEventsHandler();
        handler.setCounter(this.counter);
        this.publisher.subscribe(handler);

        this.events = new ArrayList<DomainEvent>();
        final AllEventsCollector logging = new AllEventsCollector(this.events);
        this.publisher.subscribe(logging);

        //reset counter
        this.counter.set(0L);
    }

    @Test
    public void publishingOnTheSameThreadWorks() throws Exception {

        final String id = "id-345";
        final CarId carId = new CarId(id);
        final Car forum = new Car(carId);
        forum.removeAllTires();
        forum.mountTiresOf(19);

        assertEquals(counter.longValue(), 1);
        assertEquals(this.events.size(), 1);
        assertNotNull(this.events.get(0).occurredOn());
        assertEquals(((TireSizeChanged) this.events.get(0)).getPlateId(),
                     new PlateId(carId.getId()));
    }

    @Test
    public void publishingOnDifferentThreadDoesntWork() throws Exception {

        final Runnable tireChanger = new Runnable() {

            @Override
            public void run() {

                final String id = "id-345";
                final CarId carId = new CarId(id);
                final Car forum = new Car(carId);
                forum.removeAllTires();
                forum.mountTiresOf(19);
            }
        };

        final ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(tireChanger);
        executorService.shutdownNow();

        Thread.sleep(200);

        assertEquals(counter.longValue(), 0);
        assertEquals(this.events.size(), 0);
    }

    private class AllEventsCollector implements DomainEventSubscriber<DomainEvent> {

        private final Class<DomainEvent> clazz;

        private final List<DomainEvent> events;

        public AllEventsCollector(final List<DomainEvent> events) {

            this.events = events;
            this.clazz = DomainEvent.class;
        }

        @Override
        public Class<DomainEvent> subscribedToEventType() {

            return this.clazz;
        }

        @Override
        public void handleEvent(final DomainEvent aDomainEvent) {

            this.events.add(aDomainEvent);

        }
    }

    private class LocalCustomisationEventsHandler implements DomainEventSubscriber<TireSizeChanged> {

        private AtomicLong counter;

        public void setCounter(AtomicLong counter) {

            this.counter = counter;
        }

        @Override
        public Class<TireSizeChanged> subscribedToEventType() {

            return TireSizeChanged.class;
        }

        @Override
        public void handleEvent(TireSizeChanged aDomainEvent) {
            this.counter.incrementAndGet();
        }
    }
}
