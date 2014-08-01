package org.mox.spikes.domain.guice;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import org.mox.spikes.domain.customisation.Car;
import org.mox.spikes.domain.customisation.CarId;
import org.mox.spikes.domain.events.TireSizeChanged;
import org.mox.spikes.domain.infrastructure.DomainEventSubscriber;
import org.mox.spikes.domain.repository.Repository;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class TestServicesDependenciesModule extends AbstractModule {

    private DomainEventSubscriber<TireSizeChanged> tireSizeChangedHandler;

    private Repository<CarId, Car> carRepository;

    public TestServicesDependenciesModule(
            final DomainEventSubscriber<TireSizeChanged> tireSizeChangedHandler,
            final Repository<CarId, Car> carRepository) {

        this.tireSizeChangedHandler = tireSizeChangedHandler;
        this.carRepository = carRepository;
    }

    @Override
    protected void configure() {

        bind(new TypeLiteral<DomainEventSubscriber<TireSizeChanged>>() {})
                .toInstance(this.tireSizeChangedHandler);

        bind(new TypeLiteral<Repository<CarId, Car>>() {})
                .toInstance(this.carRepository);

    }
}
