package org.example.httpserver;

import org.example.httpserver.config.Configuration;
import org.example.httpserver.config.ConfigurationManager;
import org.example.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpServer {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
    public static void main(String[] args) throws IOException {
        LOGGER.info("Server starting...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Port: " + configuration.getPort());
        LOGGER.info("Webroot: " + configuration.getWebroot());

        ServerListenerThread serverListenerThread = new ServerListenerThread(configuration.getPort(), configuration.getWebroot());
        serverListenerThread.start();
    }
}
