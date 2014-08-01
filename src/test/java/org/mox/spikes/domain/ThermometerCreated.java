package org.mox.spikes.domain;

import org.joda.time.DateTime;
import org.mox.spikes.domain.apis.DomainEvent;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ThermometerCreated implements DomainEvent {

    private final int eventVersion;

    private final DateTime occurredOn;

    private final String id;

    private final String temperature;

    public ThermometerCreated(final String id, final String aTemperature) {

        this.eventVersion = 1;
        this.occurredOn = new DateTime();
        this.id = id;
        this.temperature = aTemperature;
    }

    public String getId() {

        return id;
    }

    public String getTemperature() {

        return temperature;
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

        return "ThermometerCreated{" +
                "eventVersion=" + eventVersion +
                ", occurredOn=" + occurredOn +
                ", id='" + id + '\'' +
                ", temperature='" + temperature + '\'' +
                '}';
    }
}
