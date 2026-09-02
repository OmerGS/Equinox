package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import utils.Port;

public class ProxyWorker implements Runnable {
    private Socket client;
    private int destinationPort;

    public ProxyWorker(Socket clientSocket, int destinationPort) {
        try {
            Port.CheckPort(destinationPort);

            if (clientSocket == null) {
                throw new IllegalArgumentException("The client cannot be null");
            }
            
            this.destinationPort = destinationPort;
            this.client = clientSocket;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    @Override
    public void run() {
        try {
            Socket serverSocket = new Socket("localhost", this.destinationPort);
            
            InputStream clientIn = client.getInputStream();
            OutputStream clientOut = client.getOutputStream();
            InputStream serverIn = serverSocket.getInputStream();
            OutputStream serverOut = serverSocket.getOutputStream();

            Thread clientToServer = new Thread(() -> {
                try {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = clientIn.read(buffer)) != -1) {
                        serverOut.write(buffer, 0, bytesRead);
                        serverOut.flush();
                    }
                } catch (Exception e) { 

                } finally {
                    closeSockets(client, serverSocket);
                }
            });

            Thread serverToClient = new Thread(() -> {
                try {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = serverIn.read(buffer)) != -1) {
                        clientOut.write(buffer, 0, bytesRead);
                        clientOut.flush();
                    }
                } catch (IOException e) { 

                } finally {
                    closeSockets(client, serverSocket);
                }
            });

            clientToServer.start();
            serverToClient.start();

        } catch (Exception e) {
            System.err.println("Erreur Proxy : " + e.getMessage());
        }
    }

    private void closeSockets(Socket s1, Socket s2) {
        try { 
            if (s1 != null && !s1.isClosed()) s1.close(); 
        } catch (IOException e) {}
        
        try { 
            if (s2 != null && !s2.isClosed()) s2.close(); 
        } catch (IOException e) {}
    }
}
