package bearmaps.proj2ab;

public class Node<ItemType>{
    private double priority;
    private ItemType item;

    public static <ItemType> Node<ItemType> CreateNode(){
        return new Node<>();
    }

    public static <ItemType> Node<ItemType> CreateNode(ItemType item, double priority){
        return new Node<>(item, priority);
    }

    public Node(){
        this.item = null;
        this.priority = 0;
    }

    public Node(ItemType item, double priority){
        this.item = item;
        this.priority = priority;
    }

    public double GetPriority() {
        return priority;
    }

    public ItemType GetItem() {
        return item;
    }

    public void SetItem(ItemType item){
        this.item = item;
    }

    public void SetPriority(double priority){
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "(" + item.toString() + ", " + priority +")";
    }
}