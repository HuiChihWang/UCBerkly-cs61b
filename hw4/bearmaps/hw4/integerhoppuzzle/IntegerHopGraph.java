package bearmaps.hw4.integerhoppuzzle;

import bearmaps.hw4.AStarGraph;
import bearmaps.hw4.WeightedEdge;

import java.util.ArrayList;
import java.util.List;

/**
 * The Integer Hop puzzle implemented as a graph.
 * Created by hug.
 */
public class IntegerHopGraph implements AStarGraph<Integer> {
    private double ADD_WEIGHT = 1.;
    private double MULTIPLY_WEIGHT = 5.;
    private double SQUARE_WEIGHT = 10.;

    @Override
    public List<WeightedEdge<Integer>> neighbors(Integer v) {
        ArrayList<WeightedEdge<Integer>> neighbors = new ArrayList<>();
        neighbors.add(new WeightedEdge<>(v, v * v, SQUARE_WEIGHT));
        neighbors.add(new WeightedEdge<>(v, v * 2, MULTIPLY_WEIGHT));
        neighbors.add(new WeightedEdge<>(v, v / 2, MULTIPLY_WEIGHT));
        neighbors.add(new WeightedEdge<>(v, v - 1, ADD_WEIGHT));
        neighbors.add(new WeightedEdge<>(v, v + 1, ADD_WEIGHT));
        return neighbors;
    }

    @Override
    public double estimatedDistanceToGoal(Integer s, Integer goal) {
        // possibly fun challenge: Try to find an admissible heuristic that
        // speeds up your search. This is tough!
        double addRouteWeight = getAddEstimate(s, goal);
        double multiplyRouteWeight = getMultiplyEstimate(s, goal);
        double squareRouteWeight = getSquareEstimate(s, goal);

        return Math.min(addRouteWeight, Math.min(multiplyRouteWeight, squareRouteWeight));
    }

    private double getAddEstimate(Integer s, Integer goal){
        return Math.abs(goal - s) * ADD_WEIGHT;
    }

    private double getMultiplyEstimate(Integer s, Integer goal){
        return Math.abs(Math.log((double)(goal) / (double)(s)) / Math.log(2.)) * MULTIPLY_WEIGHT;
    }

    private double getSquareEstimate(Integer s, Integer goal){
        if(goal >= s){
            return Math.log((double) goal) / Math.log((double) s) * 0.5 * SQUARE_WEIGHT;
        }
        return Double.POSITIVE_INFINITY;
    }

    public static void main(String[] args){
        AStarGraph<Integer> g = new IntegerHopGraph();

        g.estimatedDistanceToGoal(17, 111);
    }
}
