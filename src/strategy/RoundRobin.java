package strategy;

import java.util.ArrayList;
import java.util.List;
import model.BackendServer;
import strategy.interfaces.RoutingStrategy;

public class RoundRobin implements RoutingStrategy {
    private int index;

    @Override
    public int selectNextServer(List<BackendServer> serverList) {
        List<BackendServer> onlineServer = new ArrayList<BackendServer>();
        
        for (BackendServer backendServer : serverList) {
            if(backendServer.getStatus() == true){
                onlineServer.add(backendServer);
            }
        }

        int redirectionPort = onlineServer.get(index % onlineServer.size()).getPort();
        this.index++;

        return(redirectionPort);
    }
}