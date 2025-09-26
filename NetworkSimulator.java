import java.util.*;

public class NetworkSimulator {

    public static List<Router> createNetwork(Scanner scanner) {
        List<Router> routers = new ArrayList<>();
        Router A = new Router("A");
        Router B = new Router("B");
        Router C = new Router("C");
        Router D = new Router("D");

        routers.add(A);
        routers.add(B);
        routers.add(C);
        routers.add(D);

        connect(scanner, A, B);
        connect(scanner, A, C);
        connect(scanner, B, C);
        connect(scanner, B, D);
        connect(scanner, C, D);

        return routers;
    }

    private static void connect(Scanner scanner, Router r1, Router r2) {
        System.out.println("Enter bandwidth (kbps) and delay (ms) for link " + r1.name + " - " + r2.name);
        System.out.print("  Bandwidth (kbps): ");
        int bw = scanner.nextInt();
        System.out.print("  Delay (ms): ");
        int delay = scanner.nextInt();

        int cost = EIGRPCalculator.calculateCost(bw, delay);
        r1.addNeighbor(r2, cost);
        r2.addNeighbor(r1, cost); // bidirectional
    }

    // Dijkstra algorithm with next-hop tracking
    public static Map<Router, Integer> dijkstra(Router start, List<Router> allRouters, Map<Router, Router> nextHopMap) {
        Map<Router, Integer> distances = new HashMap<>();
        for (Router r : allRouters) distances.put(r, Integer.MAX_VALUE);
        distances.put(start, 0);
        nextHopMap.put(start, start);

        PriorityQueue<Router> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        queue.add(start);

        while (!queue.isEmpty()) {
            Router current = queue.poll();

            for (Map.Entry<Router, Integer> entry : current.neighbors.entrySet()) {
                Router neighbor = entry.getKey();
                int cost = entry.getValue();
                int newCost = distances.get(current) + cost;

                if (newCost < distances.get(neighbor)) {
                    distances.put(neighbor, newCost);
                    queue.add(neighbor);

                    // Track next hop
                    if (current == start) {
                        nextHopMap.put(neighbor, neighbor);
                    } else {
                        nextHopMap.put(neighbor, nextHopMap.get(current));
                    }
                }
            }
        }

        return distances;
    }
}
