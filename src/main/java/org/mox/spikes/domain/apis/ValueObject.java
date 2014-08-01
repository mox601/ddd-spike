package org.mox.spikes.domain.apis;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public interface ValueObject<T> {

    boolean sameValueAs(final T other);

}
