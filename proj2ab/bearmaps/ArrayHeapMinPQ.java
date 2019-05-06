package bearmaps;
import javax.annotation.processing.SupportedSourceVersion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

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
        if(size() == 0){
            throw new NoSuchElementException();
        }

        return removeInNode(arrayMinHeap, ROOT_IDX).GetItem();
    }

    @Override
    public void changePriority(ItemType item, double priority) {
        Integer findIdx = findNodeIdx(arrayMinHeap, item, ROOT_IDX);
        if(findIdx == null)
            throw new NoSuchElementException();

        if(arrayMinHeap.get(findIdx).GetPriority() == priority)
            return;

        Node<ItemType> modifyNode = removeInNode(arrayMinHeap, findIdx);
        modifyNode.SetPririty(priority);

        add(item, priority);
    }

    @Override
    public boolean contains(ItemType item) {
        Integer findIdx = findNodeIdx(arrayMinHeap, item, ROOT_IDX);
        return findIdx != null;
    }

    @Override
    public int size() {
        return arrayMinHeap.size()-1;
    }

    public void printHeap(){
        PrintHeapDemo.printSimpleArrayListHeap(arrayMinHeap);
    }

    private void MinHeapifyFromBottom(ArrayList<Node<ItemType>> arrayHeap, int startIdx){
        if(startIdx == ROOT_IDX)
            return;

        int parentIdx = getParentIdx(startIdx);

        if(arrayHeap.get(parentIdx).GetPriority() > arrayHeap.get(startIdx).GetPriority()){
            Collections.swap(arrayHeap, startIdx, parentIdx);
            MinHeapifyFromBottom(arrayHeap, parentIdx);
        }
    }

    private void MinHeapifyFromTop(ArrayList<Node<ItemType>> arrayHeap, int startIdx){
        int leftIdx = getLeftIdx(startIdx);
        int rightIdx = getRightIdx(startIdx);

        if(leftIdx >= arrayHeap.size() && rightIdx >= arrayHeap.size()){
            return;
        }

        if(rightIdx >= arrayHeap.size() && leftIdx <= arrayHeap.size()-1){
            if(arrayHeap.get(leftIdx).GetPriority() < arrayHeap.get(startIdx).GetPriority()){
                Collections.swap(arrayHeap, startIdx, leftIdx);
            }
            return;
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

    private Node<ItemType> removeInNode(ArrayList<Node<ItemType>> arrayHeap, int removeIdx){
        Collections.swap(arrayHeap, removeIdx, arrayHeap.size()-1);
        Node<ItemType> removeNode = arrayMinHeap.remove(arrayMinHeap.size()-1);

        MinHeapifyFromTop(arrayMinHeap, removeIdx);

        if(removeIdx != ROOT_IDX) {
            if (arrayHeap.get(removeIdx).GetPriority() < arrayHeap.get(getParentIdx(removeIdx)).GetPriority()) {
                MinHeapifyFromBottom(arrayHeap, removeIdx);
            }
        }
        return removeNode;
    }

    private Integer findNodeIdx(ArrayList<Node<ItemType>> arrayHeap, ItemType item, int startIdx){
        if(startIdx >= arrayHeap.size()){
            return null;
        }

        if(arrayHeap.get(startIdx).GetItem().equals(item)){
            return startIdx;
        }

        Integer findLeftIdx = findNodeIdx(arrayHeap, item, getLeftIdx(startIdx));
        Integer findRightIdx = findNodeIdx(arrayHeap, item, getRightIdx(startIdx));

        if(findLeftIdx != null)
            return findLeftIdx;
        if(findRightIdx != null)
            return findRightIdx;

        return null;
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
