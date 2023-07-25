package model;

import java.util.ArrayList;
import java.util.TreeSet;

public interface GeneticSetting {
    public ArrayList<Tour> selection(TreeSet<Tour> pool, double selectivePressure);
    public Tour crossover(Tour tour1, Tour tour2) throws Exception;
    public Tour mutation(Tour tour) throws Exception;
}
