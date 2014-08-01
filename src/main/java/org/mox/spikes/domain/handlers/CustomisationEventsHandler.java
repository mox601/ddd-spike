package org.mox.spikes.domain.handlers;

import com.google.inject.Inject;
import org.mox.spikes.domain.events.TireSizeChanged;
import org.mox.spikes.domain.infrastructure.DomainEventSubscriber;
import org.mox.spikes.domain.registration.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * from iddd, pag. 287
 * Handlers can be lightweight:
 * simple,
 * event storing subscriber,
 * immediate forwarding subscriber, (2pc)
 * remote subscriber
 *
 * immediate forwarding needs 2pc when forwarding to message queue
 * that implies a remote subscriber
 *
 * another role is the forwarder, that takes events data store
 * and writes to the message queue
 *
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class CustomisationEventsHandler implements DomainEventSubscriber<TireSizeChanged> {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            CustomisationEventsHandler.class);

    private final Class<TireSizeChanged> clazz;

    private final RegistrationService registrationService;

    @Inject
    public CustomisationEventsHandler(final RegistrationService registrationService) {
        // event class it is subscribed to
        this.clazz = TireSizeChanged.class;
        this.registrationService = registrationService;
    }

    @Override
    public Class<TireSizeChanged> subscribedToEventType() {

        return this.clazz;
    }

    @Override
    public void handleEvent(final TireSizeChanged tireSizeChangedEvent) {

        this.registrationService
                .changeTireSizeOnDrivingLicence(tireSizeChangedEvent.getPlateId(),
                                                tireSizeChangedEvent.getSize());
    }

}
