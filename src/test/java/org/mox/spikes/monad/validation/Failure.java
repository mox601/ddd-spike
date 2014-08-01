package org.mox.spikes.monad.validation;

import com.google.common.base.Function;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class Failure<L, A> extends Validation<L, A> {

    protected final L left;

    private Failure(A value, L left) {

        super(value);
        this.left = left;
    }

    @Override
    public <B> Validation<L, B> map(Function<? super A, ? extends B> mapper) {

        return failure(left, mapper.apply(value));
    }

    @Override
    public <B> Validation<L, B> flatMap(Function<? super A, Validation<?, ? extends B>> mapper) {

        final Validation<?, ? extends B> result = mapper.apply(value);

        return result.isSuccess() ?
                failure(left, result.value) :
                failure(((Failure<L, B>) result).left, result.value);

    }

    @Override
    public boolean isSuccess() {

        return false;
    }

    public static <L, B> Failure<L, B> failure(L left, B value) {

        return new Failure<L, B>(value, left);
    }
}
