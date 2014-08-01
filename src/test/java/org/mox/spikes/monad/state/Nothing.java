package org.mox.spikes.monad.state;

import com.google.common.base.Function;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class Nothing<A> extends Maybe<A> {

    private static <B> Nothing<B> nothing() {

        return new Nothing<B>();
    }

    @Override
    public A value() {

        return null;
    }

    @Override
    public <B> Maybe<B> map(Function<A, Maybe<B>> mapper) {

        return nothing();
    }
}
