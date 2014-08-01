package org.mox.spikes.domain;

import org.joda.time.DateTime;
import org.mox.spikes.domain.apis.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ThermometerTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            ThermometerTestCase.class);

    @Test
    public void testRebuildOfEventSourcedAggregate() throws Exception {

        final List<DomainEvent> somePastEvents = new ArrayList<DomainEvent>();
        final String id = "id";
        final String healthyBodyTemperature = "36.2 Celsius";
        final TemperatureIncreased value =
                new TemperatureIncreased(id,
                                         healthyBodyTemperature,
                                         new DateTime());
        somePastEvents.add(value);
        //rebuild aggregate from eventstream
        final Thermometer someEventSourcedInstance = new Thermometer(somePastEvents, 1);
        assertNotNull(someEventSourcedInstance);
        assertEquals(someEventSourcedInstance.getTemperature(), healthyBodyTemperature);

    }

    @Test
    public void testCtor() throws Exception {

        final String initialTemperature = "37.2 Celsius";
        final Thermometer someEventSourcedInstance =
                new Thermometer("id", initialTemperature);
        assertEquals(someEventSourcedInstance.getTemperature(), initialTemperature);

    }
}
