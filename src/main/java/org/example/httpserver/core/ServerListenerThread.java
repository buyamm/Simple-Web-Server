package org.example.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

;

public class ServerListenerThread extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    private int port;
    private String webroot;
    private ServerSocket serverSocket;

    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket client = serverSocket.accept();
                ProcessingThread p = new ProcessingThread(client);
                p.start();
                LOGGER.info(" * Connection accepted: " + client.getInetAddress());


//                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
