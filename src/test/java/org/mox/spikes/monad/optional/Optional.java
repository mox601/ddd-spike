package org.mox.spikes.monad.optional;

import com.google.common.base.Function;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class Optional<T> {

    private static final Optional<?> EMPTY = new Optional(null);

    private final T value;

    public Optional(T value) {

        this.value = value;
    }

    public <U> Optional<U> map(final Function<? super T, ? extends U> function) {

        final Optional<U> optional = new Optional(function.apply(value));
        return emptyIfNull(optional);
    }

    public <U> Optional<U> flatMap(final Function<? super T, Optional<U>> function) {

        final Optional<U> applied = function.apply(value);
        return emptyIfNull(applied);

    }

    public T or(final T defaultValue) {

        return this.value != null ? this.value : defaultValue;
    }

    private <U> Optional emptyIfNull(final Optional<U> optional) {

        final Optional empty = EMPTY;
        return value == null ? empty : optional;
    }
}
