package org.mox.spikes.domain.customisation;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.mox.spikes.domain.customisation.commands.ApplyCustomTires;
import org.mox.spikes.domain.events.TireSizeChanged;
import org.mox.spikes.domain.guice.InMemoryEventStoreModule;
import org.mox.spikes.domain.guice.ServicesModule;
import org.mox.spikes.domain.guice.TestServicesDependenciesModule;
import org.mox.spikes.domain.infrastructure.DomainEventSubscriber;
import org.mox.spikes.domain.repository.Repository;
import org.mox.spikes.domain.repository.events.EventStore;
import org.mox.spikes.domain.repository.events.StoredEvent;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.easymock.EasyMock.*;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class CustomisationServiceTestCase {

    private CustomisationService customisationService;

    private DomainEventSubscriber<TireSizeChanged> tireSizeChangedDomainEventHandler;

    private Repository<CarId, Car> carRepository;

    private EventStore eventStore;

    @SuppressWarnings("unchecked")
    @BeforeMethod
    public void initInstanceUnderTestAndDependencies() throws Exception {

        //mock dependencies
        this.carRepository = createStrictMock(Repository.class);
        this.tireSizeChangedDomainEventHandler = createStrictMock(DomainEventSubscriber.class);

        final Injector injector = Guice.createInjector(new ServicesModule(),
                                                       new InMemoryEventStoreModule(),
                                                       new TestServicesDependenciesModule(
                                                               this.tireSizeChangedDomainEventHandler,
                                                               this.carRepository));

        this.customisationService = injector.getInstance(CustomisationService.class);
        assertNotNull(this.customisationService);

        this.eventStore = injector.getInstance(EventStore.class);
        assertNotNull(this.eventStore);

    }

    @Test
    public void shouldTestCustomTiresCommandHandling() throws Exception {

        reset(this.carRepository, this.tireSizeChangedDomainEventHandler);

        setupExpectations();

        replay(this.carRepository, this.tireSizeChangedDomainEventHandler);

        final ApplyCustomTires applyCustomTires = new ApplyCustomTires(new CarId("1"), 19);
        this.customisationService.handle(applyCustomTires);

        verify(this.carRepository, this.tireSizeChangedDomainEventHandler);
    }

    private void setupExpectations() throws Exception {

        this.carRepository.getById(anyObject(CarId.class));
        expectLastCall().andReturn(new Car(new CarId("1"))).once();

        this.tireSizeChangedDomainEventHandler.subscribedToEventType();
        expectLastCall().andReturn(TireSizeChanged.class).once();

        this.tireSizeChangedDomainEventHandler.handleEvent(anyObject(TireSizeChanged.class));
        expectLastCall().once();

        this.carRepository.update(anyObject(CarId.class), anyObject(Car.class));
        expectLastCall().andReturn(new Car(new CarId("1"))).once();

    }

    @Test
    public void shouldHandleMultipleCustomTiresCommands() throws Exception {

        reset(this.carRepository, this.tireSizeChangedDomainEventHandler);

        final int commandsAmount = 20;
        setupExpectations(commandsAmount);

        replay(this.carRepository, this.tireSizeChangedDomainEventHandler);

        for (int i = 0; i < commandsAmount; i++) {
            final ApplyCustomTires applyCustomTires = new ApplyCustomTires(new CarId("1"), 19);
            this.customisationService.handle(applyCustomTires);
        }

        verify(this.carRepository, this.tireSizeChangedDomainEventHandler);

        // event store
        final List<StoredEvent> storedEvents = this.eventStore.allStoredEventsSince(0L);
        assertEquals(storedEvents.size(), commandsAmount);
    }

    private void setupExpectations(final int amount) throws Exception {

        //order matters
        for (int i = 0; i < amount; i++) {
            this.carRepository.getById(anyObject(CarId.class));
            expectLastCall().andReturn(new Car(new CarId("1"))).times(1);

            this.tireSizeChangedDomainEventHandler.subscribedToEventType();
            expectLastCall().andReturn(TireSizeChanged.class).times(1);

            this.tireSizeChangedDomainEventHandler.handleEvent(anyObject(TireSizeChanged.class));
            expectLastCall().times(1);

            this.carRepository.update(anyObject(CarId.class), anyObject(Car.class));
            expectLastCall().andReturn(new Car(new CarId("1"))).times(1);
        }

    }
}

