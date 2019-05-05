import java.util.Map;
import java.util.HashMap;

import java.util.List;
import java.util.ArrayList;

public class MyTrieSet implements TrieSet61B {

    private Node trieRoot;

    public MyTrieSet(){
        clear();
    }

    @Override
    public void clear() {
        trieRoot = new Node(null, false);
    }

    @Override
    public void add(String key) {
        addInNode(trieRoot, key);
    }

    @Override
    public boolean contains(String key) {
        return containsInNode(trieRoot, key);
    }

    @Override
    public String longestPrefixOf(String key) {
        return longestPrefixOfInNode(trieRoot, key, "", "");
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> prefixKeys = new ArrayList<>();
        Node start = findNodeWithPrefix(trieRoot, prefix);

        if(start != null){
            collectPrefix(start, prefix, prefixKeys);
        }
        return prefixKeys;
    }

    private void addInNode(Node root, String str){
        if(str.isEmpty()){
            root.isKey = true;
            return;
        }

        if(!root.nextMap.containsKey(str.charAt(0))){
            root.nextMap.put(str.charAt(0), Node.CreateNode(str.charAt(0), false));
        }

        addInNode(root.nextMap.get(str.charAt(0)), str.substring(1));
    }

    private boolean containsInNode(Node root, String key){
        if(key.isEmpty())
            return root.isKey;

        if(!root.nextMap.containsKey(key.charAt(0)))
            return false;

        return containsInNode(root.nextMap.get(key.charAt(0)), key.substring(1));
    }

    private void collectPrefix(Node root, String subKey, List<String> keyBuffer){
        if(root.isKey)
            keyBuffer.add(subKey);

        for(char childCh: root.nextMap.keySet()){
            collectPrefix(root.nextMap.get(childCh), subKey+childCh, keyBuffer);
        }
    }

    private Node findNodeWithPrefix(Node root, String prefix){
        if(prefix.isEmpty())
            return root;

        if(!root.nextMap.containsKey(prefix.charAt(0)))
            return null;

        return findNodeWithPrefix(root.nextMap.get(prefix.charAt(0)), prefix.substring(1));
    }

    private String longestPrefixOfInNode(Node root, String subkey, String traversed, String longestPrefix){
        if(subkey.isEmpty())
            return longestPrefix;

        if(root.nextMap.containsKey(subkey.charAt(0))) {
            Node next = root.nextMap.get(subkey.charAt(0));
            traversed += next.character;

            if(next.isKey)
                longestPrefix = traversed;

            return longestPrefixOfInNode(next, subkey.substring(1), traversed, longestPrefix);
        }
        else{
            return longestPrefix;
        }
    }

    private static class Node{
        public Character character;
        public boolean isKey;
        public Map<Character, Node> nextMap;


        public  static Node CreateNode(Character ch, boolean isKey){
            return new Node(ch, isKey);
        }


        public Node(Character c, boolean isKey){
            this.character = c;
            this.isKey = isKey;
            nextMap = new HashMap<>();
        }
    }

}
