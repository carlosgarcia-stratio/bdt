package com.stratio.qa.clients;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.stratio.qa.clients.cct.DeployApiTest;
import com.stratio.qa.specs.CommonG;
import com.stratio.qa.utils.ThreadProperty;
import org.mockserver.configuration.ConfigurationProperties;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.socket.PortFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseClientTest {

    protected final Logger logger = LoggerFactory
            .getLogger(DeployApiTest.class);

    protected ClientAndServer mockServer;

    protected int port;

    protected CommonG commong;

    protected void startMockServer() throws Exception {
        ConfigurationProperties.logLevel("ERROR");
        port  = PortFactory.findFreePort();
        logger.info("Starting mock server...");
        mockServer = ClientAndServer.startClientAndServer(port);
        logger.info("Mock sever started.");
    }

    protected void setHTTPClient() {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        commong = new CommonG();
        commong.setClient(new AsyncHttpClient(new AsyncHttpClientConfig.Builder().setAcceptAnyCertificate(true).setAllowPoolingConnections(false).build()));
    }

    protected abstract <T extends BaseClient> T getClient();

    protected void stopMockServer() {
        logger.info("Stopping mock server...");
        mockServer.stop();
        logger.info("Mock sever stopped.");
    }

}
