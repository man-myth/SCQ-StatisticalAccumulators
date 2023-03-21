import java.util.HashMap;

public class ManufacturingSystem {
    public static void main(String[] args) {
        HashMap<Integer, Double[]> machineParts = new HashMap<>();
        machineParts.put(1, new Double[]{0.0, 1.73, 2.90});
        machineParts.put(2, new Double[]{1.73, 1.35, 1.76});
        machineParts.put(3, new Double[]{3.08, 0.71, 3.39});
        machineParts.put(4, new Double[]{3.79, 0.62, 4.52});
        machineParts.put(5, new Double[]{4.41, 14.28, 4.46});
        machineParts.put(6, new Double[]{18.69, 0.70, 4.36});
        machineParts.put(7, new Double[]{19.39, 15.52, 2.07});
        machineParts.put(8, new Double[]{34.91, 3.15, 3.36});
        machineParts.put(9, new Double[]{38.06, 1.76, 2.37});
        machineParts.put(10, new Double[]{39.82, 1.00, 5.38});
        machineParts.put(11, new Double[]{40.82, null, null});
    }

    
}
