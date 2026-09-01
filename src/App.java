import dummy.DummyServer;
import java.util.Arrays;
import java.util.List;
import strategy.RoundRobin;
import strategy.interfaces.RoutingStrategy;

public class App {
    public static void main(String[] args) throws Exception {
        strategy();
        dummy();
    }

    private static void strategy() throws Exception {
        RoutingStrategy strategy = new RoundRobin();
        List<Integer> ports = Arrays.asList(8081, 8082);

        strategy.savePorts(ports);
        
        for(int i = 0; i < 5; i++) {
            System.out.println(strategy.selectNextServer());
        }
    }

    private static void dummy() throws Exception {
        DummyServer titane = new DummyServer("titane", 8082);
        DummyServer aluminium = new DummyServer("aluminium", 8083);

        Thread threadTitane = new Thread(titane);
        Thread threadAluminium = new Thread(aluminium);

        threadTitane.start();
        threadAluminium.start();

        System.out.println("Serveurs on");
    }
}
