package org.mox.spikes.domain;

import org.mox.spikes.domain.apis.DomainEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public abstract class EventSourcedAggregate {

    private static final String WHEN_METHOD_NAME = "when";

    private int unmutatedVersion;

    private List<DomainEvent> mutatingEvents;

    protected EventSourcedAggregate() {

        this.setMutatingEvents(new ArrayList<DomainEvent>(2));
    }

    protected EventSourcedAggregate(final List<DomainEvent> somePastEvents,
                                    final int aStreamVersion) {

        super();

        for (final DomainEvent aDomainEvent : somePastEvents) {

            this.mutateWhen(aDomainEvent);
        }
        // no events are added to mutating events, since this
        // is already reconstructing an instance from past events

        this.setUnmutatedVersion(aStreamVersion);

    }

    protected void mutateWhen(final DomainEvent aDomainEvent) {

        final Class<? extends EventSourcedAggregate> aRootType = this.getClass();

        final Class<? extends DomainEvent> anEventType = aDomainEvent.getClass();

        Method method = null;

        try {

            method = ClassUtils.hiddenOrPublicMethod(aRootType, anEventType);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            if (method != null) {
                method.setAccessible(true);
                method.invoke(this, aDomainEvent);
            }


        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setUnmutatedVersion(final int unmutatedVersion) {

        this.unmutatedVersion = unmutatedVersion;
    }

    public void setMutatingEvents(final ArrayList<DomainEvent> mutatingEvents) {

        this.mutatingEvents = mutatingEvents;
    }

    protected void apply(final DomainEvent aDomainEvent) {

        this.getMutatingEvents().add(aDomainEvent);

        this.mutateWhen(aDomainEvent);
    }

    public List<DomainEvent> getMutatingEvents() {

        return mutatingEvents;
    }

    public int getUnmutatedVersion() {

        return unmutatedVersion;
    }

    public int mutatedVersion() {

        return this.getUnmutatedVersion() + 1;

    }

    public static class ClassUtils {

        public ClassUtils() {

        }

        private static Method hiddenOrPublicMethod(
                Class<? extends EventSourcedAggregate> aRootType,
                Class<? extends DomainEvent> anEventType)
                throws Exception {

            Method method = null;

            try {

                // assume protected or private...

                method = aRootType.getDeclaredMethod(
                        WHEN_METHOD_NAME,
                        anEventType);

            } catch (Exception e) {

                // then public...

                method = aRootType.getMethod(
                        WHEN_METHOD_NAME,
                        anEventType);
            }

            return method;
        }

    }


}
