package helpers.api.cas.mock;

import helpers.api.cas.service.HealthCheckService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author jtremeaux
 */
public class MockHealthCheckService {
    public static int userCount;

    /**
     * Create a mock of LoginService.
     *
     * @return The mock
     */
    public static HealthCheckService create() {
        HealthCheckService healthCheckService = mock(HealthCheckService.class);

        when(healthCheckService.healthCheck()).thenAnswer(i -> true);

        return healthCheckService;
    }
}
