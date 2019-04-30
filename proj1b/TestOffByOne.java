import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();
    // Your tests go here.

    @Test
    public void testOffByOne(){
        assertTrue(offByOne.equalChars('a','b'));
        assertTrue(offByOne.equalChars('r','s'));
        assertTrue(offByOne.equalChars('n','m'));
        assertTrue(offByOne.equalChars('z','y'));
        assertTrue(offByOne.equalChars('A','B'));
        assertTrue(offByOne.equalChars('N','M'));
        assertTrue(offByOne.equalChars('Z','Y'));

        assertFalse(offByOne.equalChars('a','a'));
        assertFalse(offByOne.equalChars('c','f'));
        assertFalse(offByOne.equalChars('z','m'));

        assertTrue(offByOne.equalChars('%','&'));


    }
} /*Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/