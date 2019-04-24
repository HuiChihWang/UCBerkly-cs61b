package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private PercolationFactory PercolationCreator;
    private int ExperimentTimes;
    private int TestGridSize;
    private double[] arrayPercolationThresh;


    public PercolationStats(int N, int T, PercolationFactory pf){
        if(N <= 0 || T <= 0)
            throw new IllegalArgumentException();

        PercolationCreator = pf;
        ExperimentTimes = T;
        TestGridSize = N;
        arrayPercolationThresh = new double[ExperimentTimes];

        Simulate();
    }

    private void Simulate(){
        for(int i=0; i<ExperimentTimes; ++i)
            arrayPercolationThresh[i] = SimulateOneTime();

    }

    private double SimulateOneTime(){
        Percolation pTester = PercolationCreator.make(TestGridSize);
        while(!pTester.percolates())
            RandomOpenSite(pTester);

        return (double)pTester.numberOfOpenSites() / (double)(TestGridSize*TestGridSize);
    }

    private void RandomOpenSite(Percolation pTester){
        int[] blockPos = ChooseRandomBlockSite(pTester);
        pTester.open(blockPos[0], blockPos[1]);
    }

    private int[] ChooseRandomBlockSite(Percolation pTester){
        int row = StdRandom.uniform(TestGridSize);
        int col = StdRandom.uniform(TestGridSize);

        while(pTester.isOpen(row, col)){
            row = StdRandom.uniform(TestGridSize);
            col = StdRandom.uniform(TestGridSize);
        }
        return new int[]{row, col};
    }

    public double mean(){
        return StdStats.mean(arrayPercolationThresh);
    }

    public double stddev(){
        return StdStats.stddev(arrayPercolationThresh);
    }

    public double confidenceLow(){
        return mean() - 1.96*stddev()/Math.sqrt((double)ExperimentTimes);
    }

    public double confideHigh(){
        return mean() + 1.96*stddev()/Math.sqrt((double)ExperimentTimes);
    }

}
