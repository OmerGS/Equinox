package strategy.interfaces;

import exceptions.NotValidPortNumber;
import java.util.List;

public interface RoutingStrategy {
    /**
     * Initialize the routing strategy 
     * @param ports List of server's port
     */
    public void savePorts(List<Integer> ports) throws NotValidPortNumber;
    
    /**
     * Select the next available server
     * @return The port of the server
     */
    public int selectNextServer();
}
