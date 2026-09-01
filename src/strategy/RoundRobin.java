package strategy;

import exceptions.NotValidPortNumber;
import java.util.ArrayList;
import java.util.List;
import strategy.interfaces.RoutingStrategy;

public class RoundRobin implements RoutingStrategy {
    private final List<Integer> ports = new ArrayList<>();
    private int index;

    @Override
    public void savePorts(List<Integer> ports) throws NotValidPortNumber {
        if (ports == null) throw new NotValidPortNumber("Ports list is not null");

        for (Integer port : ports) {
            if (port < 0 || port > 65535) throw new NotValidPortNumber("A port can only be between 0 and 65535.");
            this.ports.add(port);
        }
    }

    @Override
    public int selectNextServer() {
        int redirectionPort = this.ports.get(index % this.ports.size());
        this.index++;

        return(redirectionPort);
    }
}