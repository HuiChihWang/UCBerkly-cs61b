public class Node<KeyType, ValueType> {
    private KeyType key;
    private ValueType value;
    private Node<KeyType, ValueType> left;
    private Node<KeyType, ValueType> right;


    public static <KeyType,ValueType>  Node<KeyType,ValueType> CreateNode(KeyType key, ValueType value){
        return new Node<>(key, value);
    }

    public Node(KeyType key, ValueType value){
        this.key = key;
        this.value = value;
    }

    public void SetLeftNode(Node<KeyType, ValueType> node){
        left = node;
    }

    public void SetRightNode(Node<KeyType, ValueType> node){
        right = node;
    }

    public Node GetLeftNode(){
        return left;
    }

    public Node GetRightNode(){
        return right;
    }

    public KeyType GetKey(){
        return key;
    }

    public ValueType GetValue(){
        return value;
    }

}
