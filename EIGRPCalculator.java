public class EIGRPCalculator {
    // EIGRP metric: (10^7 / bandwidth in kbps) + (delay in ms / 10)
    public static int calculateCost(int bandwidth, int delay) {
        return (10000000 / bandwidth) + (delay / 10);
    }
}
