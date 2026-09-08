package strategy.interfaces;

import java.util.List;
import model.BackendServer;

public interface RoutingStrategy { 
    /**
     * Select the next available server
     * @return The port of the server
     */
    public int selectNextServer(List<BackendServer> serverList);
}
