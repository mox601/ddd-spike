package org.mox.spikes.domain.repository;

import org.mox.spikes.domain.Thermometer;
import org.mox.spikes.domain.repository.events.sourcing.EventStoreProvider;
import org.mox.spikes.domain.repository.events.sourcing.EventStream;
import org.mox.spikes.domain.repository.events.sourcing.EventStreamId;
import org.mox.spikes.domain.repository.exceptions.RepositoryException;

import java.util.UUID;

/**
 * store, update and delete are merged in "save":
 * different api from classic "domain events" repository.
 *
 * it stores events of a mutated event sourced instance, not the actual instance
 *
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class EventStoreInMemThermometerRepository extends EventStoreProvider
        implements EventStoreRepository<String, Thermometer> {

    public EventStoreInMemThermometerRepository() {

        //TODO pass eventstore as dependency?
        super();
    }

    @Override
    public void save(final String id, final Thermometer entity) throws RepositoryException {

        EventStreamId eventStreamId =
                new EventStreamId(
                        entity.getId(),
                        entity.mutatedVersion());

        this.eventStore().appendWith(eventStreamId, entity.getMutatingEvents());

    }

    @Override
    public Thermometer byId(final String id) throws RepositoryException {

        // snapshots not currently supported; always use version 1

        final EventStreamId eventStreamId = new EventStreamId(id);

        final EventStream eventStream = this.eventStore().eventStreamSince(eventStreamId);

        final Thermometer thermometer = new Thermometer(eventStream.events(),
                                                        eventStream.version());

        return thermometer;

    }

    @Override
    public String nextIdentity() {

        return UUID.randomUUID().toString();
    }
}
