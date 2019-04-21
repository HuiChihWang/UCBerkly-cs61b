package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Double> arb = new ArrayRingBuffer<>(5);
        assertTrue(arb.isEmpty());

        arb.enqueue(5.);
        arb.enqueue(10.);
        arb.enqueue(11.);

        assertEquals(3, arb.fillCount());
        assertEquals((Double)5., arb.dequeue());
        assertEquals((Double)10., arb.peek());

        arb.enqueue(5.);
        arb.enqueue(10.);
        arb.enqueue(11.);

        assertTrue(arb.isFull());
    }

    @Test
    public void iteratorTest(){
        ArrayRingBuffer<Double> arb = new ArrayRingBuffer<>(4);

        arb.enqueue(5.);
        arb.enqueue(10.);
        arb.enqueue(11.);
        arb.dequeue();
        arb.dequeue();

        arb.enqueue(1.);
        arb.enqueue(2.);
        arb.enqueue(3.);

        Double[] ans = {11., 1., 2., 3.};

        int i =0;
        for(Double item: arb){
            assertEquals(ans[i], item, 0.001);
            i+=1;
        }

        //System.out.println(arb);

        ArrayRingBuffer<Integer> a = new ArrayRingBuffer<>(3);
        ArrayRingBuffer<Double> b = new ArrayRingBuffer<>(3);
        System.out.println(a.getClass() == b.getClass());
    }
}
