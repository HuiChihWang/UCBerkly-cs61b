package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;


public class ArrayHeapMinPQTest {
    private ArrayHeapMinPQ<Integer> myArrayMinPQ;
    private NaiveMinPQ<Integer> ansArrayMinPQ;

    private void SetUp(){
        myArrayMinPQ = new ArrayHeapMinPQ<>();
        ansArrayMinPQ = new NaiveMinPQ<>();
    }

    private double RumTimeTest(ExtrinsicMinPQ<Integer> testMinPQ, TestData testCase){
        Integer[] items = testCase.items;
        double[] priorities = testCase.priorities;
        double[] changePriorities = testCase.changePriorities;

        Stopwatch sw;

        sw = new Stopwatch();
        for(int i = 0; i < testCase.testNum; ++i){
            testMinPQ.add(items[i], priorities[i]);
        }
        double addRuntime = sw.elapsedTime();

        sw = new Stopwatch();
//        for(int i = 0; i < testCase.testNum; ++i){
//            testMinPQ.changePriority(items[i], changePriorities[i]);
//        }
        double changeRuntime = sw.elapsedTime();

        sw = new Stopwatch();
        for(int i = 0; i < testCase.testNum; ++i){
            testMinPQ.removeSmallest();
        }
        double removeRuntime = sw.elapsedTime();

        System.out.printf("Add Runtime: %f, Change Runtime: %f, Remove Runtime: %f\n", addRuntime, changeRuntime, removeRuntime);
        return addRuntime + changeRuntime + removeRuntime;
    }

    private class TestData{
        public int testNum;
        public Integer[] items;
        public double[] priorities;
        public double[] changePriorities;

        public TestData(int testNum){
            this.testNum = testNum;
            createTestData();
            randomAssignTestValue();
        }

        private void createTestData(){
            items = new Integer[testNum];
            priorities = new double[testNum];
            changePriorities = new double[testNum];
        }

        private void randomAssignTestValue(){
            for(int i = 0; i < testNum; ++i){
                items[i] = i;
                priorities[i] = Math.random();
                changePriorities[i] = Math.random();
            }
        }
    }




    @Test
    public void TestMinPQConstructor(){
        SetUp();
        assertEquals(ansArrayMinPQ.size(), myArrayMinPQ.size());
    }

    @Test
    public void TestAddRemoveBasic(){
        SetUp();

        Integer[] testItem = new Integer[]{0,1,2};
        double[] testPriority = new double[]{0.5, 0.3, 0.3};
        Integer[] removeAns = new Integer[]{1,2,0};

        for(int i = 0; i < testItem.length; ++i){
            myArrayMinPQ.add(testItem[i], testPriority[i]);
        }

        myArrayMinPQ.printHeap();
        assertEquals(1, (int) myArrayMinPQ.getSmallest());

        for(int i = 0; i < testItem.length; ++i){
            assertEquals(removeAns[i], myArrayMinPQ.removeSmallest());
        }
    }

    @Test
    public void TestMinPQRandomAddRemoveSmallest(){
        SetUp();

        int testNum = 500;
        for(int i = 0; i < testNum; ++i) {
            double randomPriority = Math.random();
            ansArrayMinPQ.add(i, randomPriority);
            myArrayMinPQ.add(i, randomPriority);
        }

        assertEquals(ansArrayMinPQ.size(), myArrayMinPQ.size());
        assertEquals(ansArrayMinPQ.getSmallest(), myArrayMinPQ.getSmallest());

        for(int i = 0; i < testNum; ++i){
            assertEquals(ansArrayMinPQ.removeSmallest(), myArrayMinPQ.removeSmallest());
        }
    }

    @Test
    public void TestMinPQContains(){
        SetUp();

        for(int i = 0; i < 20; ++i){
            double randomPriority = Math.random();
            myArrayMinPQ.add(i, randomPriority);
        }

        for(int i = 0; i < 20; ++i){
            assertTrue(myArrayMinPQ.contains(i));
        }

        for(int i = 21; i < 30; ++i){
            assertFalse(myArrayMinPQ.contains(i));
        }
    }

    @Test
    public void TestChangePriorityBasic(){
        SetUp();

        Integer[] testItem = new Integer[]{0,1,2};
        double[] testPriority = new double[]{0.5, 0.3, 0.3};
        double[] testNewPriority = new double[]{0.3, 0.4, 1.};
        Integer[] removeAns = new Integer[]{0, 1, 2};

        for(int i = 0; i < testItem.length; ++i) {
            myArrayMinPQ.add(testItem[i], testPriority[i]);
        }

        for(int i = 0; i < testItem.length; ++i){
            myArrayMinPQ.changePriority(testItem[i], testNewPriority[i]);
        }

        myArrayMinPQ.printHeap();
        assertEquals(0, (int) myArrayMinPQ.getSmallest());

        for(int i = 0; i < testItem.length; ++i){
            assertEquals(removeAns[i], myArrayMinPQ.removeSmallest());
        }
    }

    @Test
    public void TestMinPQRandomChangePriority(){
        SetUp();

        int testNum = 500;
        for(int i = 0; i < testNum; ++i) {
            double randomPriority = Math.random();
            ansArrayMinPQ.add(i, randomPriority);
            myArrayMinPQ.add(i, randomPriority);
        }

        for(int i = 0; i < testNum; ++i) {
            double randomNewPriority = Math.random();
            ansArrayMinPQ.changePriority(i, randomNewPriority);
            myArrayMinPQ.changePriority(i, randomNewPriority);
        }

        for(int i = 0; i < testNum; ++i){
            assertEquals(ansArrayMinPQ.removeSmallest(), myArrayMinPQ.removeSmallest());
        }
    }

    @Test
    public void TestRunTime(){
        int testNum = 10000;
        TestData testCase = new TestData(testNum);
        SetUp();

        double totalRuntimeMyMinPQ = RumTimeTest(myArrayMinPQ, testCase);
        double totalRuntimeNaiveMinPQ = RumTimeTest(ansArrayMinPQ, testCase);

    }
}
