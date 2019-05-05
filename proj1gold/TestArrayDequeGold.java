import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    public void testArrayDequeGold_unit(){
        StudentArrayDeque<Integer> sd = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ad = new ArrayDequeSolution<>();

        Integer num_op = 30;
        Integer count = 0;
        String operation_msg = "\n";

        while(count < num_op) {
            double op_prob = StdRandom.uniform();

            if (op_prob < 0.25 && !ad.isEmpty()) {
                Integer itemSd = sd.removeFirst();
                Integer itemAd = ad.removeFirst();
                operation_msg += "removeFirst(): " + itemSd.toString() + "\n";
                assertEquals(operation_msg, itemAd, itemSd);
            } else if (op_prob >= 0.25 && op_prob < 0.5 && !ad.isEmpty()) {
                Integer itemSd = sd.removeLast();
                Integer itemAd = ad.removeLast();
                operation_msg += "removeLast(): " + itemSd.toString() + "\n";
                assertEquals(operation_msg, itemAd, itemSd);
            } else if (op_prob >= 0.5 && op_prob < 0.75) {
                sd.addFirst(count);
                ad.addFirst(count);
                operation_msg += "addFirst(" + count.toString() + ")\n";
                assertEquals(operation_msg, ad.getFirst(), sd.get(0));
            } else {
                sd.addLast(count);
                ad.addLast(count);
                operation_msg += "addLast(" + count.toString() + ")\n";
                assertEquals(operation_msg, ad.getLast(), sd.get(sd.size() - 1));
            }
            count += 1;
        }
    }

    @Test
    public void testArrayDequeGold(){
        while(true){
            testArrayDequeGold_unit();
        }
    }




}
