package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Graph {

    private static Graph instance = null;

    private HashSet<Character> allNodes;
    private HashMap<Character, HashMap<Character, Integer>> allEdges;

    public static Graph getInstance() {
        if (instance == null) {
            instance = new Graph();
        }
        return instance;
    }

    private Graph() {
        allNodes = new HashSet<>();
        allEdges = new HashMap<>();
    }

    public void setAllNodes(HashSet<Character> nodes) {
        this.allNodes = nodes;
        Iterator<Character> it = nodes.iterator();
        while (it.hasNext()) {
            this.allEdges.put(it.next(), new HashMap<>());
        }
    }

    public void addDistance(char node1, char node2, int distance) {
        this.allEdges.get(node1).put(node2, distance);
    }

    public HashSet<Character> getNodes() {
        return allNodes;
    }

    public int getDistance(char node1, char node2) {
        return allEdges.get(node1).get(node2);
    }
}
