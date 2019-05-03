/**
 * A String-like class that allows users to add and remove characters in the String
 * in constant time and have a constant-time hash function. Used for the Rabin-Karp
 * string-matching algorithm.
 */

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Iterator;

class RollingString{

    private Deque<Character> dequeChar;
    private int maximumLength;
    private int hashSum;
    private int maxBase;
    /**
     * Number of total possible int values a character can take on.
     * DO NOT CHANGE THIS.
     */
    static final int UNIQUECHARS = 128;

    /**
     * The prime base that we are using as our mod space. Happens to be 61B. :)
     * DO NOT CHANGE THIS.
     */
    static final int PRIMEBASE = 6113;

    /**
     * Initializes a RollingString with a current value of String s.
     * s must be the same length as the maximum length.
     */
    public RollingString(String s, int length) {
        assert(s.length() == length);

        maximumLength = length;
        dequeChar = new ArrayDeque<>();

        int hashAcc = 0;
        maxBase = 1;

        for(int idx = 0; idx < s.length(); ++idx) {
            dequeChar.add(s.charAt(idx));
            hashSum = UNIQUECHARS*hashSum + (int)s.charAt(idx);
            maxBase *= UNIQUECHARS;
        }
    }

    /**
     * Adds a character to the back of the stored "string" and 
     * removes the first character of the "string". 
     * Should be a constant-time operation.
     */
    public void addChar(char c) {
        dequeChar.add(c);
        char cRm = dequeChar.remove();

        hashSum  = UNIQUECHARS * hashSum -  (int)cRm * maxBase + (int) c;

    }


    /**
     * Returns the "string" stored in this RollingString, i.e. materializes
     * the String. Should take linear time in the number of characters in
     * the string.
     */
    public String toString() {
        StringBuilder strb = new StringBuilder();
        for(char c: dequeChar)
            strb.append(c);

        return strb.toString();
    }

    /**
     * Returns the fixed length of the stored "string".
     * Should be a constant-time operation.
     */
    public int length() {
        return maximumLength;
    }


    /**
     * Checks if two RollingStrings are equal.
     * Two RollingStrings are equal if they have the same characters in the same
     * order, i.e. their materialized strings are the same.
     */
    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        if(o.getClass() != this.getClass())
            return false;

        RollingString oRollingString = (RollingString) o;

        if(oRollingString.length() != length())
            return false;

        return toString().equals(oRollingString.toString());
    }

    /**
     * Returns the hashcode of the stored "string".
     * Should take constant time.
     */
    @Override
    public int hashCode() {
        return hashSum % PRIMEBASE;
    }

    public static void main(String[] Args){
        int x = 1;
        for(int i = 1; i <= 14; ++i)
            System.out.println(x *= 128);

        String a = "abc";
        System.out.println(a.charAt(0));

    }
}
