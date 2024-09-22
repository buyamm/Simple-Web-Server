package org.example.httpserver;

import org.example.httpserver.config.Configuration;
import org.example.httpserver.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    public static void main(String[] args) throws IOException {
        System.out.println("Server starting...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();

        System.out.println("Port: " + configuration.getPort());
        System.out.println("Webroot: " + configuration.getWebroot());

        try {
            final String CRLF = "\n\r";
            ServerSocket serverSocket = new ServerSocket(configuration.getPort());
            Socket client = serverSocket.accept();

            InputStream inputStream = client.getInputStream();
            OutputStream outputStream = client.getOutputStream();

//            TODO we would read


//            TODO we would writing
            String html = "<html>" +
                    "<head><title>Simple Java HTTP Server</title></head>" +
                    "<body>" +
                        "<h1>Hello world</h1>" +
                    "</body>" +
                    "</html>";
            String response =
                    "HTTP/2 200 OK" + CRLF + // STATUS LINE - chuẩn của HTTP
                    "Content-Length: " + html.getBytes().length + CRLF + // header
                        CRLF + // biểu thị đã gửi xong header
                        html +
                        CRLF + CRLF
                    ;

            outputStream.write(response.getBytes());

            inputStream.close();
            outputStream.close();
            client.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
