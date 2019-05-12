import edu.princeton.cs.algs4.Queue;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSortAlgs {

    private Queue<Integer> GetTestIntegerData(){
        Integer[] testIntegers = new Integer[]{5,2,3,4,1,8,7,9,0};
        return convertArrayToQueue(testIntegers);
    }

    private Queue<String> GetTestStringData(){
        String[] testString = new String[]{"Gilbert", "Sandy", "Jacky", "Andy", "Chargo", "Judy"};
        return convertArrayToQueue(testString);
    }


    @Test
    public void testQuickSort() {
        assertTrue(isSorted(QuickSort.quickSort(GetTestIntegerData())));
        assertTrue(isSorted(QuickSort.quickSort(GetTestStringData())));
    }

    @Test
    public void testMergeSort() {
        assertTrue(isSorted(MergeSort.mergeSort(GetTestIntegerData())));
        assertTrue(isSorted(MergeSort.mergeSort(GetTestStringData())));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }

    private static <Item> Queue<Item> convertArrayToQueue(Item[] testData){
        Queue<Item> testQueue = new Queue<>();
        for(Item i: testData){
            testQueue.enqueue(i);
        }
        return testQueue;
    }
}
