import core.LoadBalancer;
import dummy.DummyServer;
import java.util.Arrays;
import java.util.List;
import strategy.RoundRobin;
import strategy.interfaces.RoutingStrategy;

public class App {
    public static void main(String[] args) throws Exception {
        //strategy();
        //dummy();
        loadbalance();
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

    private static void loadbalance() throws Exception {
        RoutingStrategy strategy = new RoundRobin();
        List<Integer> ports = Arrays.asList(8081, 8082);
        strategy.savePorts(ports);

        DummyServer titane = new DummyServer("titane", 8081);
        DummyServer aluminium = new DummyServer("aluminium", 8082);

        Thread threadTitane = new Thread(titane);
        Thread threadAluminium = new Thread(aluminium);

        threadTitane.start();
        threadAluminium.start();

        System.out.println("Serveurs on");

        LoadBalancer load = new LoadBalancer(8080, strategy);
        Thread threadLoadBalancer = new Thread(load);
        threadLoadBalancer.start();

        System.out.println("LoadBalancer demarré");
    }
}
