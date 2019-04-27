import java.util.Iterator;
import java.util.Set;

public class BSTMap<KeyType extends Comparable<KeyType>, ValueType> implements Map61B<KeyType, ValueType> {
    private Node<KeyType, ValueType> BSTRoot;
    private int numNodes;

    public BSTMap(){
        clear();
    }

    public void clear(){
        numNodes = 0;
        BSTRoot = null;
    }

    public boolean containsKey(KeyType key){
        return GetRecursive(BSTRoot, key) != null;
    }

    public ValueType get(KeyType key){
        Node<KeyType, ValueType> find_node = GetRecursive(BSTRoot, key);
        if(find_node == null)
            return null;

        return find_node.GetValue();
    }

    public int size(){
        return numNodes;
    }

    public void put(KeyType key, ValueType value){
        BSTRoot = PutRecursive(BSTRoot, key, value);
        numNodes += 1;
    }


    public Set<KeyType> keySet(){
        throw new UnsupportedOperationException();
    }

    public ValueType remove(KeyType key, ValueType value){
        throw new UnsupportedOperationException();
    }

    public ValueType remove(KeyType key){
        throw new UnsupportedOperationException();
    }

    public Iterator<KeyType> iterator(){
        throw new UnsupportedOperationException();
    }

    public void printInOrder(){
        printInOrderRecursive(BSTRoot);
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
        System.out.println(root);
        printInOrderRecursive(root.GetRightNode());
    }
}
