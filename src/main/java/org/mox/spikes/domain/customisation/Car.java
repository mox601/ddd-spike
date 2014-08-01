package org.mox.spikes.domain.customisation;

import org.joda.time.DateTime;
import org.mox.spikes.domain.apis.DomainEventHandler;
import org.mox.spikes.domain.apis.Entity;
import org.mox.spikes.domain.registration.Plate;
import org.mox.spikes.domain.registration.PlateId;
import org.mox.spikes.domain.events.ColorChanged;
import org.mox.spikes.domain.apis.DomainEvent;
import org.mox.spikes.domain.infrastructure.DomainEventPublisher;
import org.mox.spikes.domain.events.TireSizeChanged;
import org.mox.spikes.domain.utils.Closure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
//todo should it really implement a domain event handler?
public class Car implements Entity<Car>, DomainEventHandler<DomainEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Car.class);

    private CarId id;

    private PlateId plateId;

    private int tireSize;

    private String color;

    public Car(CarId id) {

        this(id, new PlateId(id.getId()));
    }

    public Car(final CarId id, final PlateId plateId) {

        this.id = id;
        this.plateId = plateId;
        this.tireSize = 0;
        this.color = "";
    }

    public CarId getId() {

        return id;
    }

    @Override
    public void apply(final DomainEvent change) {

        //TODO how to save changes to eventStore?
        // maybe I can have a distinct class that stores to eventStore
        // and THEN applies changes to the entity

        if (change instanceof TireSizeChanged) {

            int size = ((TireSizeChanged) change).getSize();
//            LOGGER.info("size: " + size);
            this.tireSize = size;
        }

        if (change instanceof ColorChanged) {
            final String color = ((ColorChanged) change).color();
//            LOGGER.info("color: " + color);
            this.color = color;
        }
    }

    @Override
    public boolean sameIdentityAs(final Car other) {

        return this.id.sameValueAs(other.getId());
    }

    public void assign(final Plate plateToAssign) {

        this.plateId = plateToAssign.getId();
    }

    public int getTireSize() {

        return tireSize;
    }

    public String getColor() {

        return color;
    }

    public void removeAllTires() {

        this.tireSize = -1;
    }

    public void mountTiresOf(final int tireSize) {

//        LOGGER.info("mounting tires " + tireSize);

        if (this.tireSize == -1) {

            this.tireSize = tireSize;

            final TireSizeChanged aDomainEvent = new TireSizeChanged(this.plateId, tireSize,
                                                                     new DateTime());
            DomainEventPublisher.getInstance().publish(aDomainEvent);
            //TODO consider using super.storeEvent(...)
            // http://trystans.blogspot.it/2012/01/my-summary-of-cqrs-and-ddd.html
        } else {
            throw new UnsupportedOperationException("tires should be removed first");
        }

    }

    public PlateId getPlateId() {

        return plateId;
    }

    //not really happy with this approach, too much code with improvements
    //http://cyrille.martraire.com/2011/03/thinking-functional-programming-with-map-and-fold-in-your-everyday-java/
    public static abstract class BaseClosure<E extends DomainEvent> {

        protected final DomainEventHandler<E> handler;

        public BaseClosure(final DomainEventHandler<E> handler) {

            this.handler = handler;
        }
    }

    public static class CarClosure extends BaseClosure<DomainEvent>
            implements Closure<DomainEvent, Car> {

        private final Car car;

        public CarClosure(final Car car, final DomainEventHandler<DomainEvent> handler) {

            super(handler);
            this.car = car;
        }

        @Override
        public Car execute(final DomainEvent value) {

            super.handler.apply(value);
            return this.car;
        }
    }
}
