package org.mox.spikes.monad.transaction;

import com.google.common.base.Function;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public abstract class Transaction<T> {

    private T t;

    public static void unit() {

    }

    //function application
    public <S> Transaction<S> map(final Function<T, Transaction<S>> mapper) {

        return mapper.apply(t);
    }

    //composition
    public <S, T> Function<Transaction<S>, Transaction<T>> flatMap(
            Function<S, T> mapper) {

        return null;
    }

}
