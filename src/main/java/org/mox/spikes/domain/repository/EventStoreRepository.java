package org.mox.spikes.domain.repository;

import org.mox.spikes.domain.repository.exceptions.RepositoryException;

/**
 * force T to be an EventSourcedAggregate?
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public interface EventStoreRepository<ID, T> {

    public void save(ID id, T entity) throws RepositoryException;

    public T byId(ID id) throws RepositoryException;

    public ID nextIdentity();

}
