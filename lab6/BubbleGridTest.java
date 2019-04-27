import org.junit.Test;
import static org.junit.Assert.*;

public class BubbleGridTest {

    private int[][] createTestBoard1(){
        int[] row0 = new int[]{1,1,0};
        int[] row1 = new int[]{1,0,0};
        int[] row2 = new int[]{1,1,0};
        int[] row3 = new int[]{1,1,1};
        return new int[][]{row0, row1, row2, row3};
    }

    private int[][] createDarts1(){
        int[] t1 = new int[]{2,2};
        int[] t2 = new int[]{2,0};

        return new int[][]{t1, t2};
    }


    @Test
    public void testPopBubblesPerDart(){
        BubbleGrid bubbleTester = BubbleGrid.CreateBubbleGrid(createTestBoard1());
        int bubbleFall = bubbleTester.popBubblesPerDart(2,0);
        assertEquals(4,bubbleFall);
        assertEquals(3,bubbleTester.GetBubbleNum());

        bubbleFall = bubbleTester.popBubblesPerDart(1,2);
        assertEquals(0, bubbleFall);
        assertEquals(3, bubbleTester.GetBubbleNum());

        bubbleFall = bubbleTester.popBubblesPerDart(0,0);
        assertEquals(1,bubbleFall);
        assertEquals(1, bubbleTester.GetBubbleNum());
    }

    @Test
    public void testPopBubbleDarts(){
        BubbleGrid bubbleTester = BubbleGrid.CreateBubbleGrid(createTestBoard1());
        int[][] darts = createDarts1();

        int[] bubbleFall = bubbleTester.popBubbles(darts);
        assertArrayEquals(new int[]{0, 4}, bubbleFall);
    }


}
