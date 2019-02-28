package helpers.api.cas.mock;

import helpers.api.cas.service.ValidationService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author jtremeaux
 */
public class MockValidationService {
    public static int userCount;

    /**
     * Create a mock of LoginService.
     *
     * @return The mock
     */
    public static ValidationService create() {
        ValidationService validationService = mock(ValidationService.class);

        when(validationService.validate(any(String.class))).thenAnswer(i -> {
            String ticket = i.getArgument(0);
            return "casuser" + (userCount++);
        });

        return validationService;
    }
}
