package org.example.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ProcessingThread extends Thread{
    private Socket client;
    private final static Logger LOGGER = LoggerFactory.getLogger(ProcessingThread.class);

    public ProcessingThread(Socket client){
        this.client = client;
    }
    @Override
    public void run() {
        try {
            final String CRLF = "\n\r";
            InputStream inputStream = client.getInputStream();
            OutputStream outputStream = client.getOutputStream();

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
                            CRLF + CRLF;

            outputStream.write(response.getBytes());

            inputStream.close();
            outputStream.close();
            client.close();

            LOGGER.info("Connection Processing Finished.");
        }catch (IOException e){

        }
    }
}
