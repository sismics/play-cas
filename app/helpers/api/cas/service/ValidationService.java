package helpers.api.cas.service;

import helpers.api.cas.Cas;
import okhttp3.HttpUrl;
import okhttp3.Request;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import play.Logger;
import play.libs.XML;
import play.libs.XPath;

/**
 * CAS validation service.
 * 
 * @author jtremeaux
 */
public class ValidationService {
    public Cas cas;

    public ValidationService(Cas cas) {
        this.cas = cas;
    }

    /**
     * Validate a CAS ticket.
     *
     * @param ticket The ticket to validate
     * @return The user name
     */
    public String validate(String ticket) {
        Request request = new Request.Builder()
                .url(HttpUrl.parse(Cas.getCasUrl() + "/serviceValidate?service")
                        .newBuilder()
                        .setQueryParameter("service", Cas.getCasService())
                        .setQueryParameter("ticket", ticket)
                        .build())
                .get()
                .build();
        return cas.execute(request, (r) -> {
                    Document doc = XML.getDocument(r.body().byteStream());
                    Node node0 = XPath.selectNodes("//*", doc).get(1);
                    if ("cas:authenticationSuccess".equals(node0.getNodeName())) {
                        Node node1 = XPath.selectNodes("//*", node0).get(1);
                        return node1.getTextContent().trim();
                    } else {
                        Logger.error("CAS validation error" + node0.getTextContent());
                        return null;
                    }
                },
                (r) -> {
                    throw new RuntimeException("Error getting validation response, response was: " + r.body().string());
                });
    }
}
