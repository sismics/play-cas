package helpers.api.cas.service;

import helpers.api.cas.Cas;
import okhttp3.HttpUrl;
import okhttp3.Request;
import play.Logger;

import java.time.Duration;

/**
 * CAS validation service.
 * 
 * @author jtremeaux
 */
public class HealthCheckService {
    public Cas cas;

    public HealthCheckService(Cas cas) {
        this.cas = cas;
    }

    /**
     * Check if the CAS server is joinable.
     *
     * @return The health status
     */
    public boolean healthCheck() {
        Request request = new Request.Builder()
                .url(HttpUrl.parse(Cas.getCasUrl() + "/serviceValidate"))
                .get()
                .build();
        return cas.execute(Cas.get().getClient().newBuilder()
                        .callTimeout(Duration.ofSeconds(2))
                        .readTimeout(Duration.ofSeconds(2))
                        .writeTimeout(Duration.ofSeconds(2))
                        .connectTimeout(Duration.ofSeconds(2))
                        .build(), request, (r) -> true,
                (r) -> {
                    Logger.error("Error checking CAS health: " + r.body().string());
                });
    }
}
