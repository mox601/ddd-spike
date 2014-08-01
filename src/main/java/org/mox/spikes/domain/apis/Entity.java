package org.mox.spikes.domain.apis;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public interface Entity<T> {

    boolean sameIdentityAs(final T other);

}
