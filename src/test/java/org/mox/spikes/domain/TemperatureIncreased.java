package org.mox.spikes.domain;

import org.joda.time.DateTime;
import org.mox.spikes.domain.apis.DomainEvent;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class TemperatureIncreased implements DomainEvent {

    private final int eventVersion;

    private final String id;

    private final DateTime occurredOn;

    private String value;

    public TemperatureIncreased(String id, String value, DateTime occurredOn) {
        this.eventVersion = 1;
        this.occurredOn = occurredOn;
        this.id = id;
        this.value = value;
    }

    public String getId() {

        return id;
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

        return "TemperatureIncreased{" +
                "eventVersion=" + eventVersion +
                ", id='" + id + '\'' +
                ", occurredOn=" + occurredOn +
                ", value='" + value + '\'' +
                "} " + super.toString();
    }
}

