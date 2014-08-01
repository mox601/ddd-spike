package org.mox.spikes.domain.repository;

import org.mox.spikes.domain.repository.exceptions.RepositoryException;

import java.util.Map;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public abstract class BaseInMemoryRepository<ID, T> implements Repository<ID, T> {

    protected final Map<ID, T> snapshots;

    //TODO manage exceptions
    public BaseInMemoryRepository(final Map<ID, T> snapshots) {
        this.snapshots = snapshots;
    }

    @Override
    public void store(final ID id, final T entity) throws RepositoryException {

        this.snapshots.put(id, entity);
    }

    @Override
    public T getById(final ID id) throws RepositoryException {
        return this.snapshots.get(id);
    }

    @Override
    public T update(final ID id, final T entity) throws RepositoryException {

        final boolean idExists = this.snapshots.containsKey(id);
        if (idExists) {
            return this.snapshots.put(id, entity);
        } else {
            throw new RepositoryException("id doesn't exist: " + id.toString());
        }
    }

    @Override
    public T remove(final ID id) throws RepositoryException {
        final T removed = this.snapshots.remove(id);
        return removed;
    }

}
