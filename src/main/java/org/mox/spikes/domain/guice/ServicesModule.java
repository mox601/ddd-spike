package org.mox.spikes.domain.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.mox.spikes.domain.customisation.CustomisationService;
import org.mox.spikes.domain.handlers.DomainEventHandler;
import org.mox.spikes.domain.infrastructure.DomainEventPublisher;

import static com.google.inject.matcher.Matchers.any;
import static com.google.inject.matcher.Matchers.subclassesOf;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ServicesModule extends AbstractModule {

    @Override
    protected void configure() {

        //services
        bind(CustomisationService.class);

        //domain event publishing dependencies
        final DomainEventPublisherResetter domainEventPublisherResetter =
                //TODO check if getProvider helps
                new DomainEventPublisherResetter();
        requestInjection(domainEventPublisherResetter);

        //bind as an aspect
        bindInterceptor(
                subclassesOf(CustomisationService.class),
                any(),
                domainEventPublisherResetter);

        //TODO logging as aspect?

    }

    /**
     * aspect
     * as explained here
     * http://musingsofaprogrammingaddict.blogspot.it/2009/01/guice-tutorial-part-2-method.html
     * see also
     * <p/>
     * http://code.google.com/p/google-guice/wiki/AOP
     */

    private static class DomainEventPublisherResetter implements MethodInterceptor {

        private DomainEventHandler domainEventHandler;

        public DomainEventPublisherResetter() {
            //needed by guice
        }

        @Override
        public Object invoke(final MethodInvocation invocation) throws Throwable {

            final DomainEventPublisher instance = DomainEventPublisher.getInstance();

            instance.reset();

            instance.subscribe(this.domainEventHandler);

            return invocation.proceed();
        }

        @Inject
        public void setDomainEventHandler(final DomainEventHandler domainEventHandler) {

            this.domainEventHandler = domainEventHandler;
        }
    }
}
