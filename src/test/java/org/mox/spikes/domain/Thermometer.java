package org.mox.spikes.domain;

import org.mox.spikes.domain.apis.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class Thermometer extends EventSourcedAggregate {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thermometer.class);

    private String id;

    private String temperature;

    protected Thermometer() {

        super();
    }

    //classic constructor
    public Thermometer(final String id, final String initialTemperature) {

        //calls ctor without params
        this();
        //then applies one event
        this.apply(new ThermometerCreated(id, initialTemperature));

    }

    //events constructor
    public Thermometer(final List<DomainEvent> somePastEvents,
                       final int aStreamVersion) {

        super(somePastEvents, aStreamVersion);

    }

    public void when(final ThermometerCreated thermometerCreated) {

        this.setTemperature(thermometerCreated.getTemperature());
    }

    public void when(final TemperatureIncreased temperatureIncreased) {

        //change internal state
        this.setId(temperatureIncreased.getId());
        this.setTemperature(temperatureIncreased.getValue());
        LOGGER.info("something happened: " + temperatureIncreased.toString());
    }

    public String getId() {

        return id;
    }

    public String getTemperature() {

        return temperature;
    }

    private void setTemperature(final String value) {

        this.temperature = value;
    }

    private void setId(String id) {

        this.id = id;
    }
}
