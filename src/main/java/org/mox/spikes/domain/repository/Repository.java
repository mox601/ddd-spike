package org.mox.spikes.domain.repository;

import org.mox.spikes.domain.repository.exceptions.RepositoryException;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public interface Repository<ID, T> {

    public void store(ID id, T entity) throws RepositoryException;

    public T getById(ID id) throws RepositoryException;

    public ID nextIdentity();

    public T update(ID id, T entity) throws RepositoryException;

    public T remove(ID id) throws RepositoryException;
}
