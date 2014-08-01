package org.mox.spikes.domain.utils;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */

public interface Closure<C, R> {
    R execute(final C value);
}

