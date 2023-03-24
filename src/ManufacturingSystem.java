import org.w3c.dom.events.Event;

import java.util.*;

public class ManufacturingSystem {
    static HashMap<Integer, Double[]> machineParts = new HashMap<>();
    static ArrayList<Object> simulation = new ArrayList<>();
    static int entityno = 0;
    static double time  = 0;
    static String eventType = "Init";
    static int qt = 0;
    static int bt = 0;
    static Stack<Integer> queue = new Stack<>();
    static int inService  = 0;
//    Stack<Double> inService = new Stack<>(); // to allow empty values
    static int p = 0;
    static int n = 0;
    static double wqsum = 0;
    static double wqmax = 0;
    static double tssum = 0;
    static double tsmax = 0;

    //instantiator
    public ManufacturingSystem(){
        machineParts.put(1, new Double[]{0.0, 1.73, 2.90}); // cust no., arrival time, interarrival time, service time
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

    public void goToNextEvent(){

        int current = entityno;
        int next  = entityno + 1;
        if(eventType == "Init"){

        }
        try{
            if(machineParts.get(entityno)[1] < machineParts.get(entityno+1)[0]){

            }
        } catch (Exception ex){

        }

        if(time <= machineParts.get(entityno+1)[0]){
            entityno += 1;
            eventType = "Arr";
            queue.push(entityno);
            if(inService == 0){
                inService = queue.pop();
                bt = 1;
                n++;
            }
            qt = queue.size();
            Double[] attributes = machineParts.get(entityno);
            time = attributes[0];
        }

        System.out.println(entityno);
        System.out.println(eventType);
        System.out.println(queue);
        updateSimulationrow();
    }
    public void updateSimulationrow(){
        simulation.clear();
        simulation.add(entityno);
        simulation.add(time);
        simulation.add(eventType);
        simulation.add(qt);
        simulation.add(bt);
        simulation.add(queue);
        simulation.add(inService);
        simulation.add(p);
        simulation.add(n);
        simulation.add(wqsum);
        simulation.add(wqmax);
        simulation.add(tssum);
        simulation.add(tsmax);
    }

//    public void arrival(){
//        entityno ++;
//        Double[] attributes = machineParts.get(entityno);
//        time = attributes[0];
//        eventType = "Arr";
//        queue.push(time); // remember to leave blank for entity 1
//        bt = !inService.isEmpty();
//        qt = queue.size();
//
//    }

    // Most changes occur at departure event
//    public void departure(){
//        entityno ++;
//        Double[] attributes = machineParts.get(entityno);
//        time = attributes[2]; // should be (service + time in queue)
//        eventType = "Dep";
//        if (queue.isEmpty()) {
//            try {
//                inService.pop();
//                inService.push(time);
//            } catch(EmptyStackException exc){
//                inService.push(time);
//            }
//        } else {
//            inService.pop();
//            inService.push(queue.pop());
//        }
//        p++;
//        n++;
//        // wqsum, wqmax, tssum, tmax to be implemented in departure
//    }

        public static void main(String[] args){
            ManufacturingSystem ms = new ManufacturingSystem();
            System.out.println(ms.simulation);
            for (int i = 10; i < 10; i++) {
                ms.goToNextEvent();
            }
        }
}
