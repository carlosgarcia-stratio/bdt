package com.stratio.qa.clients.marathon;

import feign.Client;
import feign.Feign;
import feign.Feign.Builder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import mesosphere.client.common.ModelUtils;
import mesosphere.marathon.client.Marathon;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class MarathonClient {

    static class BasicAuthInterceptor implements RequestInterceptor {
        private final String cookieValue;

        public BasicAuthInterceptor(String token) {
            this.cookieValue = "dcos-acs-auth-cookie=" + token;
        }

        public void apply(RequestTemplate template) {
            template.header("Cookie", cookieValue);
        }
    }

    static class MarathonHeadersInterceptor implements RequestInterceptor {
        public void apply(RequestTemplate template) {
            template.header("Accept", "application/json");
            template.header("Content-Type", "application/json");
        }
    }

    public static Marathon getInstance(String endpoint, String cookie) {

        try {

            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs,
                                               String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs,
                                               String authType) {
                }
            } };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());


            Client client =
                    new Client.Default(sc.getSocketFactory(), new HostnameVerifier() {
                        public boolean verify(String s, SSLSession sslSession) {
                            return true;
                        }
                    });

            Builder b = Feign.builder()
                    .encoder(new GsonEncoder(ModelUtils.GSON))
                    .decoder(new GsonDecoder(ModelUtils.GSON))
                    .requestInterceptor(new MarathonHeadersInterceptor())
                    .requestInterceptor(new BasicAuthInterceptor(cookie))
                    .client(client);

            return b.target(Marathon.class, endpoint);
        } catch (Exception e) {
            return null;
        }
    }

}
