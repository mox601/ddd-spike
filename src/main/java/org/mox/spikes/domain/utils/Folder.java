package org.mox.spikes.domain.utils;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public final class Folder {

    // the poor man Fold operator
    public static <C, R> R foreach(final Iterable<C> changes, final Closure<C, R> closure) {

        R result = null;
        for (C t : changes) {
            result = closure.execute(t);
        }
        return result;
    }

}
