package org.mox.spikes.monad.validation;

import com.google.common.base.Function;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public abstract class Validation<L, A> {

    protected final A value;

    protected Validation(final A value) {

        this.value = value;
    }

    public abstract <B> Validation<L, B> map(final Function<? super A, ? extends B> mapper);

    public abstract <B> Validation<L, B> flatMap(
            final Function<? super A, Validation<?, ? extends B>> mapper);

    public abstract boolean isSuccess();

}
