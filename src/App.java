import java.util.Arrays;
import java.util.List;
import strategy.RoundRobin;
import strategy.interfaces.RoutingStrategy;

public class App {
    public static void main(String[] args) throws Exception {
        RoutingStrategy strategy = new RoundRobin();
        List<Integer> ports = Arrays.asList(8081, 8082);

        strategy.savePorts(ports);
        
        for(int i = 0; i < 5; i++) {
            System.out.println(strategy.selectNextServer());
        }
    }
}
