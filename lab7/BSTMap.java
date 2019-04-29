import java.util.*;

public class BSTMap<KeyType extends Comparable<KeyType>, ValueType> implements Map61B<KeyType, ValueType> {
    private Node<KeyType, ValueType> BSTRoot;
    private int numNodes;

    private enum PathDirection{ROOT, LEFT, RIGHT};

    public BSTMap(){
        clear();
    }

    @Override
    public void clear(){
        numNodes = 0;
        BSTRoot = null;
    }

    @Override
    public boolean containsKey(KeyType key){
        return GetRecursive(BSTRoot, key) != null;
    }

    @Override
    public ValueType get(KeyType key){
        Node<KeyType, ValueType> find_node = GetRecursive(BSTRoot, key);
        if(find_node == null)
            return null;

        return find_node.GetValue();
    }

    @Override
    public int size(){
        return numNodes;
    }

    @Override
    public void put(KeyType key, ValueType value){
        BSTRoot = PutRecursive(BSTRoot, key, value);
        numNodes += 1;
    }

    @Override
    public Set<KeyType> keySet(){
        Set<KeyType> BSTSet = new TreeSet<>();
        for(KeyType key: this){
            BSTSet.add(key);
        }
        return BSTSet;
    }

    @Override
    public ValueType remove(KeyType key, ValueType value){
        if(key == null || value == null)
            throw new IllegalArgumentException();

        if(get(key) != value)
            return null;
        return remove(key);
    }

    @Override
    public ValueType remove(KeyType key){
        Node<KeyType, ValueType> removeNode = removeRecursive(BSTRoot, null, PathDirection.ROOT, key);
        if(removeNode != null)
            numNodes -= 1;
        return removeNode.GetValue();

    }

    public Iterator<KeyType> iterator(){
        return new BSTMapIterator();
    }

    public void printInOrder(){
        printInOrderRecursive(BSTRoot);
        System.out.print("end\n");
    }

    private Node<KeyType, ValueType> CreateNode(KeyType key, ValueType value){
        return Node.CreateNode(key, value);
    }

    private Node<KeyType, ValueType> PutRecursive(Node<KeyType, ValueType> root, KeyType key, ValueType value){
        if(root == null)
            return CreateNode(key, value);

        if(key.compareTo(root.GetKey()) < 0)
            root.SetLeftNode(PutRecursive(root.GetLeftNode(), key, value));
        else  if(key.compareTo(root.GetKey()) > 0)
            root.SetRightNode(PutRecursive(root.GetRightNode(), key, value));

        return root;
    }

    private Node<KeyType, ValueType> GetRecursive(Node<KeyType, ValueType> root, KeyType key){
        if(root == null)
            return null;

        if(key.compareTo(root.GetKey()) < 0)
            return GetRecursive(root.GetLeftNode(), key);
        else if(key.compareTo(root.GetKey()) > 0)
            return GetRecursive(root.GetRightNode(), key);

        else
            return root;
    }

    private void printInOrderRecursive(Node<KeyType, ValueType> root){
        if(root == null)
            return;

        printInOrderRecursive(root.GetLeftNode());
        System.out.print(root.toString() + "->");
        printInOrderRecursive(root.GetRightNode());
    }

    private Node<KeyType, ValueType> removeRecursive(Node<KeyType, ValueType> currentRoot, Node<KeyType, ValueType> prevRoot, PathDirection dir, KeyType key){
        if(currentRoot==null)
            return null;

        if(key.compareTo(currentRoot.GetKey()) < 0)
            return removeRecursive(currentRoot.GetLeftNode(), currentRoot, PathDirection.LEFT, key);
        if(key.compareTo(currentRoot.GetKey()) > 0)
            return removeRecursive(currentRoot.GetRightNode(), currentRoot,PathDirection.RIGHT, key);


        removeNodeFromTree(currentRoot, prevRoot, dir);
        return currentRoot;
    }

    private void removeNodeFromTree(Node<KeyType, ValueType> currentRoot, Node<KeyType, ValueType> prevRoot, PathDirection dir){
        if(currentRoot.GetLeftNode() == null && currentRoot.GetRightNode()==null){
            ReconnectTree(null, prevRoot, dir);
        }

        if(currentRoot.GetLeftNode() == null && currentRoot.GetRightNode() != null){
            ReconnectTree(currentRoot.GetRightNode(), prevRoot, dir);
        }

        if(currentRoot.GetLeftNode() != null && currentRoot.GetRightNode() == null){
            ReconnectTree(currentRoot.GetLeftNode(), prevRoot, dir);
        }

        if(currentRoot.GetLeftNode() != null && currentRoot.GetRightNode() != null){
            //TODO : Random selection candidate
            Node<KeyType, ValueType> leftCandidate = PeekMaxNode(currentRoot.GetLeftNode());
            Node<KeyType, ValueType> rightCandidate = PeekMinNode(currentRoot.GetRightNode());
            KeyType key = currentRoot.GetKey();
            SwapNodeContent(leftCandidate, currentRoot);
            removeRecursive(currentRoot, prevRoot, dir, key);
        }
    }

    private void ReconnectTree(Node<KeyType, ValueType> next, Node<KeyType, ValueType> prev, PathDirection dir){
        if(dir == PathDirection.LEFT)
            prev.SetLeftNode(next);
        if(dir == PathDirection.RIGHT)
            prev.SetRightNode(next);
        if(dir == PathDirection.ROOT)
            BSTRoot = next;
    }

    private Node<KeyType, ValueType> PeekMinNode(Node<KeyType, ValueType> root){
        if(root.GetLeftNode() == null)
            return root;
        return PeekMinNode(root.GetLeftNode());
    }

    private Node<KeyType, ValueType> PeekMaxNode(Node<KeyType, ValueType> root){
        if(root.GetRightNode() == null)
            return root;
        return PeekMaxNode(root.GetRightNode());
    }

    private void SwapNodeContent(Node<KeyType, ValueType> node1, Node<KeyType, ValueType> node2){
        KeyType tempKey = node1.GetKey();
        ValueType tempValue = node2.GetValue();
        node1.SetKey(node2.GetKey());
        node1.SetValue(node2.GetValue());

        node2.SetKey(tempKey);
        node2.SetValue(tempValue);
    }


    private class BSTMapIterator implements Iterator<KeyType>{

        private Node <KeyType, ValueType> pCurrentRoot;
        private Stack<Node<KeyType, ValueType>> stackKeys;

        public BSTMapIterator(){
            pCurrentRoot = BSTRoot;
            stackKeys = new Stack<>();
        }

        @Override
        public boolean hasNext() {
            return !stackKeys.empty() || pCurrentRoot!=null;
        }

        @Override
        public KeyType next() {
            while(pCurrentRoot != null){
                stackKeys.push(pCurrentRoot);
                pCurrentRoot = pCurrentRoot.GetLeftNode();
            }

            Node <KeyType, ValueType> prevNode = stackKeys.pop();
            pCurrentRoot = prevNode.GetRightNode();
            return prevNode.GetKey();
        }
    }
}
