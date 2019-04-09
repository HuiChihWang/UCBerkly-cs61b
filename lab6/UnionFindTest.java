import org.junit.Test;
import static org.junit.Assert.*;

public class UnionFindTest {

    @Test
    public void TestInitialize(){
        UnionFind set = new UnionFind(7);
        int[] actual = new int[7];
        for(int i=0; i<7;++i)
            actual[i] = -1;

        assertArrayEquals(set.getParentsArray(), actual);
        assertFalse(set.connected(3,5));
    }

    @Test
    public void TestBasics(){
        UnionFind set = new UnionFind(7);
        int[] actual = new int[7];

        set.union(3,5);
        set.union(2,4);
        set.union(5,6);
        assertFalse(set.connected(2,3));
        assertTrue(set.connected(3,5));
        assertTrue(set.connected(3,6));
    }
}
