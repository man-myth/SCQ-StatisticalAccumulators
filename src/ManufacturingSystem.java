import java.util.*;

public class ManufacturingSystem {
    HashMap<Integer, Double[]> machineParts = new HashMap<>();
    ArrayList<Object> simulation = new ArrayList<>();
    int entityno = 0;
    double time = 0;
    String eventType = "Init";
    int qt = 0;
    int bt = 0;
    Queue<Double> queue = new LinkedList<>();
    double inService = 0;
    int p = 0;
    int n = 0;
    double wqsum = 0;
    double wqmax = 0;
    double tssum = 0;
    double tsmax = 0;
    double sq = 0;
    double sqmax = 0;
    double sb = 0;

    //instantiator
    public ManufacturingSystem() {
        machineParts.put(1, new Double[]{0.0, 2.90, 0.0}); // Key: custno, Value: [arrival time, service time, start of service]
        machineParts.put(2, new Double[]{1.73, 1.76, 0.0});
        machineParts.put(3, new Double[]{3.08, 3.39, 0.0});
        machineParts.put(4, new Double[]{3.79, 4.52, 0.0});
        machineParts.put(5, new Double[]{4.41, 4.46, 0.0});
        machineParts.put(6, new Double[]{18.69, 4.36, 0.0});
        machineParts.put(7, new Double[]{19.39, 2.07, 0.0});
        machineParts.put(8, new Double[]{34.91, 3.36, 0.0});
        machineParts.put(9, new Double[]{38.06, 2.37, 0.0});
        machineParts.put(10, new Double[]{39.82, 5.38, 0.0});
        machineParts.put(11, new Double[]{40.82, generateServiceTime(), 0.0});
        updateSimulationrow();
    }

    public void goToNextEvent() {
        // check if the next entity in the list is in the queue
        if (queue.contains(machineParts.get(entityno + 1)[0]) & eventType == "Dep") {
            ++entityno;
            entityno = getKey(inService);
            eventType = "Dep";
            double tempTime = time;
            time = machineParts.get(getKey(inService))[2] + machineParts.get(getKey(inService))[1];
            // compute for integrals
            computeIntegral(tempTime);
            inService = queue.remove();
            double arr = machineParts.get(getKey(inService))[0];
            double ser = machineParts.get(getKey(inService))[1];
            machineParts.put(getKey(inService), new Double[]{arr, ser, time});
            bt = 1;
            n++;
            p++;
            qt = queue.size();
            if (qt > sqmax) {
                sqmax = qt;
            }
            wqmax = (Math.round((time - inService) * 100.0) / 100.0);
            wqsum = (Math.round((wqmax + wqsum) * 100.0) / 100.0);
            if (tsmax < Math.round(((inService + wqsum) - tssum) * 100.0) / 100.0)
                tsmax = Math.round(((inService + wqsum) - tssum) * 100.0) / 100.0;
            tssum = inService + wqsum;
            updateSimulationrow();
            entityno = getKey(inService);

            return;
        }
        try {
            //Check if the person in queue is done
            if (machineParts.get(getKey(inService))[2] + machineParts.get(getKey(inService))[1] <= machineParts.get(entityno + 1)[0]) {
                entityno = getKey(inService);
                eventType = "Dep";
                double tempTime = time;
                time = machineParts.get(getKey(inService))[2] + machineParts.get(getKey(inService))[1];
                // compute for integrals
                computeIntegral(tempTime);
                if (!queue.isEmpty()) {
                    inService = queue.remove();
                    double arr = machineParts.get(getKey(inService))[0];
                    double ser = machineParts.get(getKey(inService))[1];
                    machineParts.put(getKey(inService), new Double[]{arr, ser, time});
                    bt = 1;
                    n++;
                    p++;
                    qt = queue.size();
                    if (qt > sqmax) {
                        sqmax = qt;
                    }
                    wqmax = Math.max((Math.round((time - inService) * 100.0) / 100.0), wqmax);
                    wqsum = ((((Math.round((time - inService) * 100.0) / 100.0) + wqsum) * 100.0) / 100.0);
                    if (tsmax < Math.round(((inService + wqsum) - tssum) * 100.0) / 100.0) {
                        tsmax = Math.round(((inService + wqsum) - tssum) * 100.0) / 100.0;
                    }
                    tssum = inService + wqsum;

                    updateSimulationrow();
                    entityno = getKey(inService);
                } else {
                    inService = -1;
                    p++;
                    bt = 0;
                    qt = queue.size();
                    if (qt > sqmax) {
                        sqmax = qt;
                    }
                    if (tsmax < Math.round(((time + wqsum) - tssum) * 100.0) / 100.0) {
                        tsmax = Math.round(((time + wqsum) - tssum) * 100.0) / 100.0;
                    }

                    tssum = time + wqsum;

                    updateSimulationrow();

                }
                return;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        // Check for arrivals
        if (time <= machineParts.get(entityno + 1)[0]) {
            double tempTime = time;
            entityno += 1;

            Double[] attributes = machineParts.get(entityno);
            time = attributes[0];
            // compute for integrals
            computeIntegral(tempTime);
            if (bt == 0) {
                inService = machineParts.get(entityno)[0];
                bt = 1;
                n++;
                double arr = machineParts.get(getKey(inService))[0];
                double ser = machineParts.get(getKey(inService))[1];
                machineParts.put(getKey(inService), new Double[]{arr, ser, time});
            } else {
                queue.add(machineParts.get(entityno)[0]);
            }

            eventType = "Arr";
            qt = queue.size();
            if (qt > sqmax) {
                sqmax = qt;
            }
            for (Map.Entry<Integer, Double[]> entry : machineParts.entrySet()) {
                Integer key = entry.getKey();
                Double[] value = entry.getValue();
                System.out.print(key + ": ");
                for (int i = 0; i < value.length; i++) {
                    System.out.print(value[i] + " ");
                }
                System.out.println();
            }
        }
        updateSimulationrow();
    }

    public void updateSimulationrow() {
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
        simulation.add(sq);
        simulation.add(sqmax);
        simulation.add(sb);
    }


    public int getKey(double value) {
        for (Map.Entry<Integer, Double[]> set : machineParts.entrySet()) {
            if (set.getValue()[0] == value) {
                return set.getKey();
            }
        }
        return 0;
    }

    public void computeIntegral(double prevTime) {
        sq += Math.round(((time - prevTime) * qt) * 100.0) / 100.0;
        sb += Math.round(((time - prevTime) * bt) * 100.0) / 100.0;

    }

    public void generateNewEntity() {
        //generate arrival time
        double interarrivalTime = generateInterArrivalTime();
        double arrivalTime = Math.round((interarrivalTime + machineParts.get(machineParts.size())[0]) * 100.0) / 100.0; //add interarrival time to arrival time of previous part.
        double serviceTime = Math.round(generateServiceTime() * 100.0) / 100.0;
        System.out.println("Adding entity no " + (machineParts.size() + 1) + "with interarrivaltime " + interarrivalTime + " prev time " + machineParts.get(machineParts.size())[0] + " service time " + serviceTime);
        machineParts.put(machineParts.size() + 1, new Double[]{arrivalTime, serviceTime, 0.0});

    }

    public double generateServiceTime() {
        Random r = new Random();
        int randInt = r.nextInt(100);
        double[][] serviceTimeProbabilities = {         //cumulative probability, minvalue, maxvalue
                {9, 1, 1.99},
                {39, 2, 2.99},
                {59, 3, 3.99},
                {89, 4, 4.99},
                {99, 5, 5.99}
        };
        double serviceTime = 0;
        for (int i = 0; i < serviceTimeProbabilities.length; i++) {
            if (randInt < serviceTimeProbabilities[i][0]) {
                double rangeMin = serviceTimeProbabilities[i][1];
                double rangeMax = serviceTimeProbabilities[i][2];
                serviceTime = (rangeMin + (rangeMax - rangeMin) * r.nextDouble());
                break;
            }
        }

        return serviceTime;
    }

    public double generateInterArrivalTime() {
        Random r = new Random();
        int randInt = r.nextInt(100);
        double[][] interArrivalProbabilities = {
                {29, 0.1, 0.99},
                {69, 1, 1.99},
                {79, 3, 3.99},
                {89, 14, 14.99},
                {99, 15, 15.99}
        };
        double arrivalTime = 0;

        for (int i = 0; i < interArrivalProbabilities.length; i++) {
            if (randInt < interArrivalProbabilities[i][0]) {
                double rangeMin = interArrivalProbabilities[i][1];
                double rangeMax = interArrivalProbabilities[i][2];
                arrivalTime = (rangeMin + (rangeMax - rangeMin) * r.nextDouble());
                break;
            }
        }
        System.out.println("interarrival: " + arrivalTime);
        return arrivalTime;
    }


//    public static void main(String[] args){
//        ManufacturingSystem ms = new ManufacturingSystem();
////        for (int i = 0; i < 40; i++) {
////            System.out.println(ms.simulation);
////            ms.goToNextEvent();
////        }
//
//        ms.generateNewEntity();
//        ms.generateNewEntity();
//        ms.generateNewEntity();
//        ms.generateNewEntity();
//        for (Map.Entry<Integer, Double[]> entry : ms.machineParts.entrySet()) {
//            Integer key = entry.getKey();
//            Double[] value = entry.getValue();
//            System.out.print(key + ": ");
//            for (int i = 0; i < value.length; i++) {
//                System.out.print(value[i] + " ");
//            }
//            System.out.println();
//        }
//    }
}





