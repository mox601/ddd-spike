package org.mox.spikes.monad.state;

import com.google.common.base.Function;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class Just<A> extends Maybe<A> {

    private final A value;

    public Just(final A object) {

        this.value = object;
    }

    @Override
    public A value() {

        return this.value;
    }

    @Override
    public <B> Maybe<B> map(final Function<A, Maybe<B>> mapper) {

        final Maybe<B> applied = mapper.apply(this.value);
        return applied;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Just just = (Just) o;

        if (value != null ? !value.equals(just.value) : just.value != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        return value != null ? value.hashCode() : 0;
    }
}
