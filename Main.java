import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Router> routers = NetworkSimulator.createNetwork(scanner);

        for (Router source : routers) {
            Map<Router, Router> nextHops = new HashMap<>();
            Map<Router, Integer> distances = NetworkSimulator.dijkstra(source, routers, nextHops);

            System.out.println("\n--- EIGRP Routing Table for Router " + source.name + " ---");
            System.out.printf("%-15s %-15s %-10s%n", "Destination", "Next Hop", "Cost");
            System.out.println("-------------------------------------------------");

            for (Router r : routers) {
                if (r == source) continue;
                String nextHop = nextHops.containsKey(r) ? nextHops.get(r).name : "Unreachable";
                String cost = distances.get(r) == Integer.MAX_VALUE ? "∞" : String.valueOf(distances.get(r));
                System.out.printf("%-15s %-15s %-10s%n", r.name, nextHop, cost);
            }
        }

        scanner.close();
    }
}
