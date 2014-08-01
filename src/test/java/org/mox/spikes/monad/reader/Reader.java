package org.mox.spikes.monad.reader;

import com.google.common.base.Function;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public abstract class Reader<C, A> {

    public abstract A apply(C i);

    public <B> Reader<C, B> map(final Function<A, B> f) {

        return new Reader<C, B>() {

            @Override
            public B apply(C c) {

                return f.apply(Reader.this.apply(c));
            }
        };
    }

    public <B> Reader<C, B> flatMap(final Function<A, Reader<C, B>> f) {

        return new Reader<C, B>() {

            @Override
            public B apply(C i) {

                return f.apply(Reader.this.apply(i)).apply(i);
            }
        };
    }


}
