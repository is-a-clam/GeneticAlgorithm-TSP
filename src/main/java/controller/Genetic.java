package controller;

import model.*;

import java.util.*;

public class Genetic {

    private GeneticSetting settings;
    private TreeSet<Tour> population;
    private int popSize;
    private double mutationRate;
    private double highMutationRate;
    private double selectivePressure;

    public static void main(String[] args) throws Exception {
        Genetic genetic = new Genetic(new ER_DM());
        genetic.setup();
        while(true) {
            genetic.iteration();
        }
    }

    public Genetic(GeneticSetting settings) {
        this.population = new TreeSet<>(new Comparator<Tour>() {
            @Override
            public int compare(Tour t1, Tour t2) {
                return t2.getDistance() - t1.getDistance();
            }
        });
        this.settings = settings;
        this.popSize = 200;
        this.mutationRate = 0.1;
        this.highMutationRate = 0.5;
        this.selectivePressure = 1.9;
    }

    public void setup() throws Exception {
        Graph graph = Graph.getInstance();
        graph.setAllNodes(new HashSet<>(Arrays.asList(Data.names)));
        for (int r = 0; r < Data.distances.length; r++) {
            for (int c = 0; c < Data.distances[r].length; c++) {
                if (r != c) {
                    graph.addDistance(Data.names[r], Data.names[c], Data.distances[r][c]);
                }
            }
        }

        for (int i = 0; i < popSize; i++) {
            ArrayList<Character> tourList = new ArrayList<>(graph.getNodes());
            do {
                Collections.shuffle(tourList);
            } while (population.contains(new Tour(tourList)));
            population.add(new Tour(tourList));
        }
    }

    public void iteration() throws Exception {
        this.iterationNum++;
        // Selection
        ArrayList<Tour> pool = settings.selection(population, selectivePressure);

        // Crossover
        Tour baby = settings.crossover(pool.get(0), pool.get(1));

        // Mutation
        if (baby.getDistance() >= 1060) {
            double currMutationRate = mutationRate;
            if (population.last().getDistance() == 1060) {
                currMutationRate = highMutationRate;
            }
            if (Math.random() < currMutationRate) {
                baby = settings.mutation(baby);
            }
        }

        int cIndex = baby.getNodes().indexOf('C');
        int lIndex = baby.getNodes().indexOf('L');
        if (Math.abs(cIndex - lIndex) == 1) {
            List<Character> sub = baby.getNodes().subList(Math.max(cIndex, lIndex), (int) (Math.random() * (baby.getNodes().size() - lIndex - 1)) + Math.max(cIndex, lIndex));
            ArrayList<Character> subArray = new ArrayList<>(sub);
            baby.getNodes().removeAll(subArray);
            int index3 = (int) (Math.random() * baby.getNodes().size());
            baby.getNodes().addAll(index3, subArray);
        }

        if (!population.contains(baby)) {
            if (baby.getDistance() <= population.first().getDistance()) {
                if (baby.getDistance() < population.last().getDistance()) {
                    System.out.println(baby);
                }
                population.pollFirst();
                population.add(baby);
            }
        }
    }
}
