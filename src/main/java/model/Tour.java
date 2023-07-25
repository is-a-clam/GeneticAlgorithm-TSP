package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Tour {

    private ArrayList<Character> nodes;
    private int distance;

    public Tour(ArrayList<Character> nodes) throws Exception {
        this.nodes = nodes;
        this.distance = 0;
    }

    private boolean isValid() {
        HashSet set = new HashSet(this.nodes);
        if (set.size() == this.nodes.size()) {
            if (set.size() == Graph.getInstance().getNodes().size()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Character> getNodes() {
        return nodes;
    }

    public int getDistance() {
        if (this.distance == 0) {
            Iterator<Character> it = nodes.iterator();
            char currNode = it.next();
            while (it.hasNext()) {
                char nextNode = it.next();
                this.distance += Graph.getInstance().getDistance(currNode, nextNode);
                currNode = nextNode;
            }
            this.distance += Graph.getInstance().getDistance(currNode, nodes.iterator().next());
        }
        return this.distance;
    }

    public void printDistances() {
        Iterator<Character> it = nodes.iterator();
        char currNode = it.next();
        while (it.hasNext()) {
            char nextNode = it.next();
            System.out.print(Graph.getInstance().getDistance(currNode, nextNode) + " ");
            currNode = nextNode;
        }
        System.out.println(Graph.getInstance().getDistance(currNode, nodes.iterator().next()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tour tour = (Tour) o;
        return this.nodes.equals(tour.nodes);
    }

    @Override
    public String toString() {
        return distance + ": " + nodes;
    }
}
