public class Palindrome {

    public Deque<Character> wordToDeque(String word){
        Deque<Character> deque_char = new LinkedListDeque<>();

        for (char c: word.toCharArray())
            deque_char.addLast(c);

        return deque_char;
    }

//    public boolean isPalindrome(String word){
//        Deque<Character> deque_char = wordToDeque(word);
//
//        while(deque_char.size() > 1){
//            if(deque_char.removeFirst() != deque_char.removeLast()){
//                return false;
//            }
//        }
//        return true;
//    }

    /* recursive solution for isPalindrome*/
    public boolean isPalindrome(String word){
        Deque<Character> deque_char = wordToDeque(word);
        return isPalindrome_recursive_deque(deque_char);
    }

    private boolean isPalindrome_recursive_deque(Deque<Character> deque_char){
        if (deque_char.size()<=1)
            return true;

        if(deque_char.removeFirst() != deque_char.removeLast())
            return false;
        else
            return isPalindrome_recursive_deque(deque_char);
    }

    /* isPalindrome by character comparator*/
    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> deque_char = wordToDeque(word);
        return isPalindrome_recursive(deque_char, cc);
    }

    private boolean isPalindrome_recursive(Deque<Character> deque_char, CharacterComparator cc){
        if (deque_char.size() <= 1)
            return true;

        if(cc.equalChars(deque_char.removeFirst(), deque_char.removeLast()))
            return isPalindrome_recursive(deque_char,cc);
        else
            return false;
    }
}
