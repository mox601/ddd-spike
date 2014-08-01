package org.mox.spikes.domain.guice;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import org.mox.spikes.domain.customisation.Car;
import org.mox.spikes.domain.customisation.CarId;
import org.mox.spikes.domain.events.TireSizeChanged;
import org.mox.spikes.domain.handlers.CustomisationEventsHandler;
import org.mox.spikes.domain.infrastructure.DomainEventSubscriber;
import org.mox.spikes.domain.registration.DrivingLicense;
import org.mox.spikes.domain.registration.PlateId;
import org.mox.spikes.domain.repository.InMemoryCarRepository;
import org.mox.spikes.domain.repository.InMemoryDrivingLicenceRepository;
import org.mox.spikes.domain.repository.Repository;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ServicesDependenciesModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(new TypeLiteral<DomainEventSubscriber<TireSizeChanged>>() {})
                .to(CustomisationEventsHandler.class);

        bind(new TypeLiteral<Repository<CarId, Car>>() {})
                .to(InMemoryCarRepository.class);

        bind(new TypeLiteral<Repository<PlateId, DrivingLicense>>() {})
                .to(InMemoryDrivingLicenceRepository.class);

    }
}
