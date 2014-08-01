package org.mox.spikes.monad.state;

import com.google.common.base.Function;

/**
 *
 * from
 * http://faustinelli.wordpress.com/2010/07/27/maybe-monad-in-java-with-example/
 * http://faustinelli.wordpress.com/2010/07/27/example-maybe-monad-in-java/
 *
 * also
 * http://logicaltypes.blogspot.it/2011/09/monads-in-java.html
 * http://warpedjavaguy.wordpress.com/2013/04/09/flatmap-on-the-monad-in-java/
 * http://tmorris.net/posts/2010-01-19-dear-java-guy-state-is-a-monad.html
 *
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public abstract class Maybe<A> {

    public static <A> Maybe<A> unit(final A anA) {

        if (anA != null) {
            return new Just<A>(anA);
        } else {
            return new Nothing<A>();
        }
    }

    public abstract A value();

    public abstract <B> Maybe<B> map(Function<A, Maybe<B>> mapper);

    public <A, B> Function<Maybe<A>, Maybe<B>> flatMap(final Function<A, B> mapper) {

        final Function<Maybe<A>, Maybe<B>> function = new Function<Maybe<A>, Maybe<B>>() {

            @Override
            public Maybe<B> apply(Maybe<A> argument) {

                final Function<A, Maybe<B>> lifted = new Function<A, Maybe<B>>() {

                    @Override
                    public Maybe<B> apply(final A forLift) {

                        return unit(mapper.apply(forLift));
                    }
                };

                return argument.map(lifted);
            }
        };
        return function;
    }
}
