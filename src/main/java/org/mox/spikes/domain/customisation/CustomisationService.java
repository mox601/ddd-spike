package org.mox.spikes.domain.customisation;

import com.google.inject.Inject;
import org.mox.spikes.domain.customisation.commands.ApplyCustomTires;
import org.mox.spikes.domain.events.TireSizeChanged;
import org.mox.spikes.domain.infrastructure.DomainEventPublisher;
import org.mox.spikes.domain.infrastructure.DomainEventSubscriber;
import org.mox.spikes.domain.repository.Repository;
import org.mox.spikes.domain.repository.exceptions.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class CustomisationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomisationService.class);

    private final Repository<CarId, Car> carRepository;

    private final DomainEventSubscriber<TireSizeChanged> customisationEventsHandler;

    @Inject
    public CustomisationService(final Repository<CarId, Car> carRepository,
                                final DomainEventSubscriber<TireSizeChanged> customisationEventsHandler) {

        this.carRepository = carRepository;
        this.customisationEventsHandler = customisationEventsHandler;
    }

    public void handle(final ApplyCustomTires applyCustomTiresCommand) {

        //it should be called as the _first_ thing in _all_ service methods
        //TODO can this be done as an aspect?
        DomainEventPublisher.subscribeToPublisherInstance(this.customisationEventsHandler);

        try {

            final Car byId = this.carRepository.getById(applyCustomTiresCommand.getCarId());

            if (byId == null) {
                throw new RuntimeException("byId can't be null");
            }

            byId.removeAllTires();
            byId.mountTiresOf(applyCustomTiresCommand.getTireSize());

            this.carRepository.update(applyCustomTiresCommand.getCarId(), byId);

        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }
}
