package org.mox.spikes.domain.events;

import org.joda.time.DateTime;
import org.mox.spikes.domain.apis.DomainEvent;
import org.mox.spikes.domain.customisation.CarId;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ColorChanged implements DomainEvent {

    private int eventVersion;

    private CarId carId;

    private String color;

    private DateTime occurredOn;

    private ColorChanged() {

    }

    public ColorChanged(CarId carId, String color, DateTime occurredOn) {

        this.eventVersion = 1;
        this.carId = carId;
        this.color = color;
        this.occurredOn = occurredOn;
    }

    @Override
    public int eventVersion() {

        return eventVersion;
    }

    @Override
    public DateTime occurredOn() {

        return occurredOn;
    }

    public CarId carId() {

        return carId;
    }

    public String color() {

        return color;
    }

    @Override
    public String toString() {

        return "ColorChanged{" +
                "eventVersion=" + eventVersion +
                ", carId=" + carId +
                ", color='" + color + '\'' +
                ", occurredOn=" + occurredOn +
                '}';
    }
}
