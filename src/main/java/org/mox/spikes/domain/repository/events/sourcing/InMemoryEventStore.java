package org.mox.spikes.domain.repository.events.sourcing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.mox.spikes.domain.apis.DomainEvent;
import org.mox.spikes.domain.repository.events.EventSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class InMemoryEventStore implements EventStore {

    private EventSerializer serializer;

    private ListMultimap<String, DomainEvent> namedStreams;

    public InMemoryEventStore() {

        this.setSerializer(EventSerializer.instance());
        this.namedStreams = ArrayListMultimap.create();
    }

    @Override
    public void appendWith(final EventStreamId aStartingIdentity,
                           final List<DomainEvent> anEvents) {

        final String streamName = aStartingIdentity.streamName();

        //TODO how to use the streamVersion?
        final int streamVersion = aStartingIdentity.streamVersion();

        int anIndex = 0;
        for (final DomainEvent anEvent : anEvents) {
            //TODO serialization
            try {
                final String aSerializedEvent = serializer().serialize(anEvent);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            /*
            * schema mysql
            * autoincrementing id
            * serialized domainEvent
            * aDomainEvent.getClass().getName())
            * anIdentity.streamName());
            * anIdentity.streamVersion() + anIndex);
            * */

            streams().put(streamName, anEvent);

            // expand in a data structure that can be queried


        }
    }

    @Override
    public void close() {

        streams().clear();

    }

    @Override
    public List<DispatchableDomainEvent> eventsSince(final long aLastReceivedEvent) {

        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public EventStream eventStreamSince(final EventStreamId anIdentity) {

        //TODO what version should we use?
        final int streamVersion = anIdentity.streamVersion();

        final List<DomainEvent> anEventsList = streams().get(
                anIdentity.streamName());

        final List<DomainEvent> aFilteredEventsList = new ArrayList<DomainEvent>();

        for (final DomainEvent domainEvent : anEventsList) {

            if (true) {
                aFilteredEventsList.add(domainEvent);
            }

        }

        return new DefaultEventStream(anEventsList, streamVersion);
    }

    @Override
    public EventStream fullEventStreamFor(final EventStreamId anIdentity) {

        final List<DomainEvent> domainEvents = streams()
                .get(anIdentity.streamName());

        return new DefaultEventStream(domainEvents, anIdentity.streamVersion());

    }

    @Override
    public void purge() {

        throw new UnsupportedOperationException("not implemented yet");

    }

    @Override
    public void registerEventNotifiable(final EventNotifiable anEventNotifiable) {

        throw new UnsupportedOperationException("not implemented yet");

    }

    private ListMultimap<String, DomainEvent> streams() {

        return this.namedStreams;
    }

    private EventSerializer serializer() {

        return this.serializer;
    }

    private void setSerializer(final EventSerializer serializer) {

        this.serializer = serializer;
    }
}
