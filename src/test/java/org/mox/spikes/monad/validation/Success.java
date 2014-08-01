package org.mox.spikes.monad.validation;

import com.google.common.base.Function;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class Success<L, A> extends Validation<L, A> {

    private Success(final A value) {

        super(value);
    }

    @Override
    public <B> Validation<L, B> map(final Function<? super A, ? extends B> mapper) {

        return success(mapper.apply(value));
    }

    @Override
    public <B> Validation<L, B> flatMap(final Function<? super A, Validation<?, ? extends B>> mapper) {

        return (Validation<L, B>) mapper.apply(value);
    }

    @Override
    public boolean isSuccess() {

        return true;
    }

    public static <L, A> Success<L, A> success(final A value) {

        return new Success<L, A>(value);
    }
}
