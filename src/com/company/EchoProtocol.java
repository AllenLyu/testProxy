package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoProtocol implements Runnable {
    private static final int BUFSIZE = 32; // Size (in bytes) of I/O buffer  
    private Socket clientSocket; // Socket connect to client  
    private Logger logger; // Server logger  

    public EchoProtocol(Socket clientSocket, Logger logger) {
        this.clientSocket = clientSocket;
        this.logger = logger;
    }

    public static void handleEchoClient(Socket clientSocket, Logger logger) {
        try {
            // Get the input and output I/O streams from socket  
            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();

            int recvMsgSize; // Size of received message  
            int totalBytesEchoed = 0; // Bytes received from client  
            byte[] echoBuffer = new byte[BUFSIZE]; // Receive Buffer  
            // Receive until client closes connection, indicated by -1
            String header = "HTTP/1.1 200 OK\n" +
                    "Server: bfe/1.0.8.18\n" +
                    "Date: Tue, 01 Nov 2016 14:21:15 GMT\n" +
                    "Content-Type: text/html; charset=utf-8\n"
                    +
                    "Content-length:chunk";
            out.write(header.getBytes());
            while ((recvMsgSize = in.read(echoBuffer)) != -1) {
                out.write(echoBuffer, 0, recvMsgSize);
                totalBytesEchoed += recvMsgSize;
            }

            logger.info("Client " + clientSocket.getRemoteSocketAddress() + ", echoed " + totalBytesEchoed + " bytes.");

        } catch (IOException ex) {
            logger.log(Level.WARNING, "Exception in echo protocol", ex);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
            }
        }
    }

    public void run() {
        handleEchoClient(this.clientSocket, this.logger);
    }
}  