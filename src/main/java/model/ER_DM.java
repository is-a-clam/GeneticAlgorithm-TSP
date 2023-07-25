package model;

import java.util.*;

public class ER_DM implements GeneticSetting {
    @Override
    public ArrayList<Tour> selection(TreeSet<Tour> pool, double selectivePressure) {
        ArrayList<Tour> output = new ArrayList<>();
        Random random = new Random();
        int index1 = Math.min((int) Math.floor((pool.size()+selectivePressure-1) * random.nextDouble()), pool.size()-1);
        output.add((Tour) pool.toArray()[index1]);
        int index2 = index1;
        while(index1 == index2) {
            index2 = Math.min((int) Math.floor((pool.size()+selectivePressure-1) * random.nextDouble()), pool.size()-1);
        }
        output.add((Tour) pool.toArray()[index2]);
        return output;
    }

    @Override
    public Tour crossover(Tour tour1, Tour tour2) throws Exception {
        ArrayList<Character> newTour = new ArrayList<>();

        HashMap<Character, HashSet<Character>> edgeList = new HashMap<>();
        Graph graph = Graph.getInstance();
        Iterator<Character> it = graph.getNodes().iterator();
        while (it.hasNext()) {
            edgeList.put(it.next(), new HashSet<>());
        }

        for (int i = 0; i < tour1.getNodes().size(); i++) {
            char currNode = tour1.getNodes().get(i);
            int prevIndex = i-1;
            if (prevIndex == -1) {
                prevIndex = tour1.getNodes().size()-1;
            }
            int nextIndex = i+1;
            if (nextIndex == tour1.getNodes().size()) {
                nextIndex = 0;
            }
            edgeList.get(currNode).add(tour1.getNodes().get(prevIndex));
            edgeList.get(currNode).add(tour1.getNodes().get(nextIndex));
        }

        for (int i = 0; i < tour2.getNodes().size(); i++) {
            char currNode = tour2.getNodes().get(i);
            int prevIndex = i-1;
            if (prevIndex == -1) {
                prevIndex = tour2.getNodes().size()-1;
            }
            int nextIndex = i+1;
            if (nextIndex == tour2.getNodes().size()) {
                nextIndex = 0;
            }
            edgeList.get(currNode).add(tour2.getNodes().get(prevIndex));
            edgeList.get(currNode).add(tour2.getNodes().get(nextIndex));
        }

        char currNode;
        if (Math.random() < 0.5) {
            currNode = tour1.getNodes().get(0);
        }
        else {
            currNode = tour2.getNodes().get(0);
        }
        newTour.add(currNode);

        HashSet<Character> unvisited = (HashSet<Character>) graph.getNodes().clone();

        while(newTour.size() < graph.getNodes().size()) {
            unvisited.remove(currNode);
            for (HashSet<Character> edges : edgeList.values()) {
                edges.remove(currNode);
            }
            int minSize = Integer.MAX_VALUE;
            ArrayList<Character> lowestConnections = new ArrayList<>();
            for (Character node : edgeList.get(currNode)) {
                int size = edgeList.get(node).size();
                if (size < minSize) {
                    minSize = size;
                    lowestConnections = new ArrayList<>();
                    lowestConnections.add(node);
                }
                if (size == minSize) {
                    lowestConnections.add(node);
                }
            }
            if (lowestConnections.isEmpty()) {
                if (unvisited.isEmpty()) {
                    break;
                }
                currNode = (char) unvisited.toArray()[(int) (Math.random()*unvisited.size())];
            }
            else {
                currNode = lowestConnections.get((int) (Math.random()*lowestConnections.size()));
            }
            newTour.add(currNode);
        }

        return new Tour(newTour);
    }

    @Override
    public Tour mutation(Tour tour) throws Exception {
        ArrayList<Character> tourArray = (ArrayList<Character>) tour.getNodes().clone();
        ArrayList<Character> subArray;

        int index1 = (int) (Math.random()*tourArray.size());
        int index2 = index1;

        while(index1 == index2) {
            index2 = (int) (Math.random()*tourArray.size());
        }
        if (index1 < index2) {
            subArray = new ArrayList<>(tourArray.subList(index1, index2));
        }
        else {
            subArray = new ArrayList<>(tourArray.subList(index2, index1));
        }
        tourArray.removeAll(subArray);
        int index3 = (int) (Math.random()*tourArray.size());
        tourArray.addAll(index3, subArray);

        return new Tour(tourArray);
    }
}
