package bearmaps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ArrayHeapMinPQ<ItemType> implements ExtrinsicMinPQ<ItemType> {

    private ArrayList<Node<ItemType>> arrayMinHeap;
    private int ROOT_IDX = 1;

    public ArrayHeapMinPQ(){
        arrayMinHeap = new ArrayList<>();
        arrayMinHeap.add(Node.CreateNode());
    }

    @Override
    public void add(ItemType item, double priority) {
        if(contains(item)) {
            throw new IllegalArgumentException();
        }

        arrayMinHeap.add(Node.CreateNode(item, priority));
        MinHeapifyFromBottom(arrayMinHeap, arrayMinHeap.size()-1);
        numItems += 1;
    }

    @Override
    public ItemType getSmallest() {
        if(size() == 0){
            throw new IllegalArgumentException();
        }
        return arrayMinHeap.get(ROOT_IDX).GetItem();
    }

    @Override
    public ItemType removeSmallest() {
        Collections.swap(arrayMinHeap, ROOT_IDX, arrayMinHeap.size()-1);
        Node<ItemType> removeNode = arrayMinHeap.remove(arrayMinHeap.size()-1);

        MinHeapifyFromTop(arrayMinHeap, ROOT_IDX);

        return removeNode.GetItem();
    }

    @Override
    public void changePriority(ItemType item, double priority) {

    }

    @Override
    public boolean contains(ItemType item) {
        return false;
    }

    @Override
    public int size() {
        return arrayMinHeap.size()-1;
    }

    private void MinHeapifyFromBottom(ArrayList<Node<ItemType>> arrayHeap, int startIdx){
        int parentIdx = getParentIdx(startIdx);

        if(parentIdx == ROOT_IDX)
            return;

        if(arrayHeap.get(parentIdx).GetPriority() > arrayHeap.get(startIdx).GetPriority()){
            Collections.swap(arrayHeap, startIdx, parentIdx);
            MinHeapifyFromBottom(arrayHeap, parentIdx);
        }
    }

    private void MinHeapifyFromTop(ArrayList<Node<ItemType>> arrayHeap, int startIdx){
        int leftIdx = getLeftIdx(startIdx);
        int rightIdx = getRightIdx(startIdx);

        if(leftIdx > size()){
            return;
        }

        if(rightIdx > size() && leftIdx <= size()){
            if(arrayHeap.get(leftIdx).GetPriority() < arrayHeap.get(startIdx).GetPriority()){
                Collections.swap(arrayHeap, startIdx, leftIdx);
                return;
            }
        }

        Node<ItemType> leftNode = arrayHeap.get(leftIdx);
        Node<ItemType> rightNode = arrayHeap.get(rightIdx);
        Node<ItemType> startNode = arrayHeap.get(startIdx);

        if(leftNode.GetPriority() >= startNode.GetPriority() && rightNode.GetPriority() >= startNode.GetPriority()){
            return;
        }

        if(leftNode.GetPriority() < rightNode.GetPriority()){
            Collections.swap(arrayHeap, startIdx, leftIdx);
            MinHeapifyFromTop(arrayHeap, leftIdx);
        }
        else{
            Collections.swap(arrayHeap,startIdx,rightIdx);
            MinHeapifyFromTop(arrayHeap, rightIdx);
        }

    }

    private int getParentIdx(int childIdx){
        return childIdx / 2;
    }

    private int getLeftIdx(int parentIdx){
        return 2 * parentIdx;
    }

    private int getRightIdx(int parentIdx){
        return 2 * parentIdx + 1;
    }

}
