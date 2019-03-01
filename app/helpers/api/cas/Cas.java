package helpers.api.cas;

import com.sismics.sapparot.function.CheckedConsumer;
import com.sismics.sapparot.function.CheckedFunction;
import com.sismics.sapparot.okhttp.OkHttpHelper;
import helpers.api.cas.mock.MockHealthCheckService;
import helpers.api.cas.mock.MockValidationService;
import helpers.api.cas.service.HealthCheckService;
import helpers.api.cas.service.ValidationService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import play.Play;

/**
 * @author jtremeaux
 */
public class Cas {
    private static Cas cas;

    private OkHttpClient client;

    private HealthCheckService healthCheckService;

    private ValidationService validationService;

    private static OkHttpClient createClient() {
        return new OkHttpClient.Builder()
                .build();
    }

    public static Cas get() {
        if (cas == null) {
            cas = new Cas();
        }
        return cas;
    }

    public Cas() {
        client = createClient();
        if (isMock()) {
            healthCheckService = MockHealthCheckService.create();
            validationService = MockValidationService.create();
        } else {
            healthCheckService = new HealthCheckService(this);
            validationService = new ValidationService(this);
        }
    }

    public OkHttpClient getClient() {
        return client;
    }

    public HealthCheckService getHealthCheckService() {
        return healthCheckService;
    }

    public ValidationService getValidationService() {
        return validationService;
    }

    public static String getCasUrl() {
        return Play.configuration.getProperty("cas.url");
    }

    public static String getCasService() {
        return Play.configuration.getProperty("cas.service");
    }

    public static boolean isMock() {
        return Boolean.parseBoolean(Play.configuration.getProperty("cas.mock", "false"));
    }

    public <T> T execute(OkHttpClient client, Request request, CheckedFunction<Response, T> onSuccess, CheckedConsumer<Response> onFailure) {
        return OkHttpHelper.execute(client, request, onSuccess, onFailure);
    }

    public <T> T execute(Request request, CheckedFunction<Response, T> onSuccess, CheckedConsumer<Response> onFailure) {
        return execute(this.client, request, onSuccess, onFailure);
    }
}
