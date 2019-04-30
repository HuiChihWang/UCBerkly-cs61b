import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    public void testArrayDequeGold_unit(){
        StudentArrayDeque<Integer> sd = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ad = new ArrayDequeSolution<>();

        Integer num_op = 30;
        Integer count = 0;

        while(count < num_op) {
            double op_prob = StdRandom.uniform();
            if (op_prob < 0.25 && !ad.isEmpty()) {
                sd.removeFirst();
                ad.removeFirst();
            } else if (op_prob >= 0.25 && op_prob < 0.5 && !ad.isEmpty()) {
                sd.removeLast();
                ad.removeLast();
            } else if (op_prob >= 0.5 && op_prob < 0.75) {
                sd.addFirst(count);
                ad.addFirst(count);
            } else {
                sd.addLast(count);
                ad.addLast(count);
            }
            count += 1;
        }

        // ad.printDeque();
        // sd.printDeque();

        assertEquals(sd.size(), ad.size());
        for(int i = 0; i < ad.size(); ++i)
            assertEquals(ad.get(i), sd.get(i));
    }

    @Test
    public void testArrayDequeGold(){
        while(true)
            testArrayDequeGold_unit();
    }




}
