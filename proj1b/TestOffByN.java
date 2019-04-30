import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByTwo = new OffByN(2);
    static CharacterComparator offByFour = new OffByN(4);

    // Your tests go here.
    @Test
    public void testOffByTwo(){
        assertTrue(offByTwo.equalChars('a','c'));
        assertTrue(offByTwo.equalChars('r','t'));
        assertTrue(offByTwo.equalChars('z','x'));

        assertFalse(offByTwo.equalChars('r','e'));
        assertFalse(offByTwo.equalChars('k','q'));
        assertFalse(offByTwo.equalChars('z','s'));
    }

    @Test
    public void testOffByFour(){
        assertTrue(offByFour.equalChars('a','e'));
        assertTrue(offByFour.equalChars('q','u'));
        assertTrue(offByFour.equalChars('z','v'));

        assertFalse(offByFour.equalChars('r','e'));
        assertFalse(offByFour.equalChars('k','q'));
        assertFalse(offByFour.equalChars('z','s'));
    }


}
