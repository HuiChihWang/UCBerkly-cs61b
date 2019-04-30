/**
 * Created by hug.
 */
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.List;
import java.util.ArrayList;

public class Experiments {

    public static void main(String[] args) {
        experiment1();
        experiment2();
        experiment3();
    }
    public static void experiment1() {
        int testDataNum = 5000;
        List<Double> listTestResultMyBST = GetMyBSTDepthResult(testDataNum);
        List<Double> listTestResultOptimalBST = GetOptimalBSTDepthResult(testDataNum);
        plotExp1TestResult(listTestResultMyBST, listTestResultOptimalBST);
    }

    public static void experiment2() {
        int initialTreeSize = 5000;
        int operationNum = 5000;
        BST<Double> BSTTester = BST.CreateBST();
        ExperimentHelper.randomInsertionDoubleBST(BSTTester, initialTreeSize);

        List<Double> avgDepthHistory = new ArrayList<>();
        avgDepthHistory.add(BSTTester.getAverageDepth());

        for(int i = 0; i < operationNum; ++i){
            ExperimentHelper.randomDeletionInsertionDoubleBST(BSTTester, "successor");
            avgDepthHistory.add(BSTTester.getAverageDepth());
        }
        plotExp23TestResult(avgDepthHistory,"Experiment2");
    }

    public static void experiment3() {
        int initialTreeSize = 5000;
        int operationNum = 5000;
        BST<Double> BSTTester = BST.CreateBST();
        ExperimentHelper.randomInsertionDoubleBST(BSTTester, initialTreeSize);

        List<Double> avgDepthHistory = new ArrayList<>();
        avgDepthHistory.add(BSTTester.getAverageDepth());

        for (int i = 0; i < operationNum; ++i) {
            ExperimentHelper.randomDeletionInsertionDoubleBST(BSTTester, "random");
            avgDepthHistory.add(BSTTester.getAverageDepth());
        }
        plotExp23TestResult(avgDepthHistory,"Experiment3");
    }

    private static List<Double> GetMyBSTDepthResult(int testDataNum){
        BST<Double> BSTDoubleTester = BST.CreateBST();
        List<Double> listAvgDepthHistory = new ArrayList<>();

        while(BSTDoubleTester.size() < testDataNum){
            Double randomDouble = ExperimentHelper.GenerateRandomDouble();
            if(!BSTDoubleTester.contains(randomDouble)){
                BSTDoubleTester.add(randomDouble);
                listAvgDepthHistory.add(BSTDoubleTester.getAverageDepth());
            }
        }
        return listAvgDepthHistory;
    }

    private static List<Double> GetOptimalBSTDepthResult(int testDataNum){
        List<Double> listAvgDepthHistory = new ArrayList<>();
        for(int addNum = 0; addNum < testDataNum; ++addNum)
            listAvgDepthHistory.add(ExperimentHelper.optimalAverageDepth(addNum));
        return listAvgDepthHistory;
    }

    private static void plotExp1TestResult(List<Double> testResultMyBST, List<Double> testResultOptimalBST){
        int testDataNum = testResultMyBST.size();
        List<Integer> testNums = getNumArrayList(testDataNum);

        XYChart testChart = new XYChartBuilder().width(800).height(600).xAxisTitle("Number of Items").yAxisTitle("Average Depth").title("Experiment1").build();
        testChart.addSeries("My BST", testNums, testResultMyBST);
        testChart.addSeries("Optimal BST", testNums, testResultOptimalBST);
        Display(testChart);
    }

    private static void plotExp23TestResult(List<Double> randomDeleteInsertResult, String Experiment){
        List<Integer> operationNums = getNumArrayList(randomDeleteInsertResult.size());


        XYChart testChart = new XYChartBuilder().width(800).height(600).xAxisTitle("Number of Operation").yAxisTitle("Average Depth").title(Experiment).build();
        testChart.addSeries("My BST", operationNums, randomDeleteInsertResult);
        Display(testChart);
    }

    private static List<Integer> getNumArrayList(int number){
        List<Integer> numArray = new ArrayList<>();
        for(int i = 0; i < number; ++i)
            numArray.add(i);
        return numArray;
    }

    private static void Display(XYChart testChart){
        new SwingWrapper(testChart).displayChart();
    }

}
