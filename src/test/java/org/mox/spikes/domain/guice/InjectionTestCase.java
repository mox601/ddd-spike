package org.mox.spikes.domain.guice;

import com.google.inject.Injector;
import org.mox.spikes.domain.customisation.CustomisationService;
import org.testng.annotations.Test;

import static com.google.inject.Guice.createInjector;
import static org.testng.Assert.assertNotNull;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class InjectionTestCase {

    @Test
    public void testDependenciesInjection() throws Exception {

        final Injector injector = createInjector(new ServicesModule(),
                                                 new InMemoryEventStoreModule(),
                                                 new ServicesDependenciesModule());
        final CustomisationService customisationService = injector.getInstance(
                CustomisationService.class);
        assertNotNull(customisationService);
    }
}
