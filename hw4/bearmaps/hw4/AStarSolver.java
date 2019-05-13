package bearmaps.hw4;

import bearmaps.proj2ab.DoubleMapPQ;
import bearmaps.proj2ab.ExtrinsicMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private SolverOutcome solveState;
    private List<Vertex> shortestPath;
    private double elapsedTime;
    private double shortestPathLength;
    private int numDequeueOperation;

    private ExtrinsicMinPQ<Vertex> vertexStatePQ;
    private HashMap<Vertex, Double> mapDistToVertex;
    private HashMap<Vertex, Vertex> mapVertexToCurrent;
    private AStarGraph<Vertex> graph;
    private Vertex startVertex;
    private Vertex endVertex;
    private double timeThreshold;

    private Stopwatch timeTracker;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout){
        graph = input;
        startVertex = start;
        endVertex = end;
        timeThreshold = timeout;
        SetUp();

        SolveAStar();
        elapsedTime = timeTracker.elapsedTime();

        setShortestPath();
        setShortestPathLength();
    }

    private void SetUp(){
        shortestPathLength = 0.;
        shortestPath = new LinkedList<>();

        numDequeueOperation = 0;
        solveState = SolverOutcome.SOLVED;
        timeTracker = new Stopwatch();
    }

    private void SolveAStar(){
        SolverInit();
        putStartVertexInQueue();

        while(!isSearchEnded()){
            Vertex current = vertexStatePQ.removeSmallest();
            relaxEdge(current);
            numDequeueOperation += 1;
        }
    }

    private void SolverInit(){
        vertexStatePQ = new DoubleMapPQ<>();
        mapDistToVertex = new HashMap<>();
        mapVertexToCurrent = new HashMap<>();
    }

    private void relaxEdge(Vertex current){
        for(WeightedEdge<Vertex> neighborEdge: graph.neighbors(current)){
            double distToCurrent = shortestDistFromStartToVertex(neighborEdge.from());
            double edgeLength = neighborEdge.weight();
            double distToNeighbor = mapDistToVertex.get(neighborEdge.to());

            if(distToCurrent + edgeLength < distToNeighbor){
                UpdateShortestDistFromStart(neighborEdge.to(), neighborEdge.from(), distToCurrent + edgeLength);
                UpdateVertexStatePQ(neighborEdge.to());
            }
        }
    }

    private void putStartVertexInQueue(){
        vertexStatePQ.add(startVertex, 0.);
        mapDistToVertex.put(startVertex, 0.);
    }

    private void UpdateShortestDistFromStart(Vertex current, Vertex prev , double newDist){
        mapDistToVertex.put(current, newDist);
        mapVertexToCurrent.put(current, prev);
    }

    private void UpdateVertexStatePQ(Vertex relaxVertex){
        double distToGoalEstimated = shortestDistFromStartToVertex(relaxVertex) + DistToGoalFromVertex(relaxVertex);

        if(vertexStatePQ.contains(relaxVertex)){
            vertexStatePQ.changePriority(relaxVertex, distToGoalEstimated);
        }
        else{
            vertexStatePQ.add(relaxVertex, distToGoalEstimated);
        }
    }

    private double DistToGoalFromVertex(Vertex vertex){
        return graph.estimatedDistanceToGoal(vertex, endVertex);
    }

    private double shortestDistFromStartToVertex(Vertex vertex){
        if(!mapDistToVertex.containsKey(vertex)){
            return Double.POSITIVE_INFINITY;
        }
        return mapDistToVertex.get(vertex);
    }

    private boolean isSearchEnded(){
        return vertexStatePQ.size() == 0 || vertexStatePQ.getSmallest().equals(endVertex) || isTimeExceed();
    }

    private boolean isTimeExceed(){
        if(timeTracker.elapsedTime() > timeThreshold){
            solveState = SolverOutcome.TIMEOUT;
            return true;
        }

        return false;
    }

    private boolean isVertexStatePQEmpty(){
        if(vertexStatePQ.size() == 0){
            solveState = SolverOutcome.UNSOLVABLE;
            return true;
        }

        return false;
    }

    private void setShortestPathLength(){
        if(solveState == SolverOutcome.SOLVED) {
            shortestPathLength = mapDistToVertex.get(endVertex);
        }
    }

    private void setShortestPath(){
        if(solveState == SolverOutcome.SOLVED){
            Vertex start = endVertex;
            while(true){
                shortestPath.add(start);
                start = mapVertexToCurrent.get(start);

                if(start.equals(startVertex)){
                    break;
                }
            }
        }
    }

    @Override
    public double explorationTime() {
        return elapsedTime;
    }

    @Override
    public double solutionWeight() {
        return shortestPathLength;
    }

    @Override
    public int numStatesExplored() {
        return numDequeueOperation;
    }

    @Override
    public List<Vertex> solution() {
        return shortestPath;
    }

    @Override
    public SolverOutcome outcome() {
        return solveState;
    }
}
