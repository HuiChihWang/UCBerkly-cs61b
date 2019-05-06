package bearmaps;

import org.junit.Test;
import static org.junit.Assert.*;


public class ArrayHeapMinPQTest {
    private ArrayHeapMinPQ<Integer> myArrayMinPQ;
    private NaiveMinPQ<Integer> ansArrayMinPQ;

    public void SetUp(){
        myArrayMinPQ = new ArrayHeapMinPQ<>();
        ansArrayMinPQ = new NaiveMinPQ<>();
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
}
