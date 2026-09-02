package core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import strategy.interfaces.RoutingStrategy;
import utils.Port;

public class LoadBalancer implements Runnable {
    private int port;
    private RoutingStrategy strategy;

    /**
     * Initiate a LoadBalancer
     * @param port Listening port
     * @param strategy The strategy used for redirect the port
     */
    public LoadBalancer(int port, RoutingStrategy strategy) {
        try {
            Port.CheckPort(port);
            if (strategy == null) {
                throw new IllegalArgumentException("La stratégie ne peut pas être null !");
            }
            
            this.port = port;
            this.strategy = strategy; 
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Start the LoadBalancer
     */
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.port);
            
            while (true) { 
                Socket clientSocket = serverSocket.accept();
                int redirectionPort = strategy.selectNextServer();

                ProxyWorker proxy = new ProxyWorker(clientSocket, redirectionPort);
                Thread proxyThread = new Thread(proxy);
                proxyThread.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
    }
}
