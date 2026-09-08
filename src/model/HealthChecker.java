package model;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class HealthChecker implements Runnable {
    public List<BackendServer> serverList;

    public HealthChecker(List<BackendServer> list) {
        this.serverList = list;
    }
    
    @Override
public void run() {
    while(true) {
        for (BackendServer server : this.serverList) {
            try (Socket s = new Socket()) {
                s.connect(new java.net.InetSocketAddress(server.getHost(), server.getPort()), 1000);
                
                if (!server.getStatus()) {
                    System.out.println("[INFO] Serveur " + server.getPort() + " est de nouveau UP !");
                }
                server.setStatus(true);
                
            } catch (IOException e) {
                if (server.getStatus()) {
                    System.err.println("[WARN] Serveur " + server.getPort() + " est DOWN !");
                }
                server.setStatus(false);
            }
        }
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            break;
        }
    }
}
}
