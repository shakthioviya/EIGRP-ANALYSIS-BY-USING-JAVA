import java.util.HashMap;
import java.util.Map;

public class Router {
    public String name;
    public Map<Router, Integer> neighbors;

    public Router(String name) {
        this.name = name;
        this.neighbors = new HashMap<>();
    }

    public void addNeighbor(Router neighbor, int cost) {
        neighbors.put(neighbor, cost);
    }

    @Override
    public String toString() {
        return name;
    }
}
