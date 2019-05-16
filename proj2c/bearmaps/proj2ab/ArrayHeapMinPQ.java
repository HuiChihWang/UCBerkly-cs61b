package bearmaps.proj2ab;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.NoSuchElementException;


public class ArrayHeapMinPQ<ItemType> implements ExtrinsicMinPQ<ItemType> {

    private ArrayList<Node> arrayMinHeap;
    private HashMap<ItemType, Integer> mapItemIdxInHeap;

    private int ROOT_IDX = 1;

    public ArrayHeapMinPQ(){
        mapItemIdxInHeap = new HashMap<>();
        arrayMinHeap = new ArrayList<>();
        arrayMinHeap.add(new Node(null, 0.));
    }

    @Override
    public void add(ItemType item, double priority) {
        if(contains(item)) {
            throw new IllegalArgumentException();
        }

        arrayMinHeap.add(new Node(item, priority));
        mapItemIdxInHeap.put(item, arrayMinHeap.size()-1);

        MinHeapifyFromBottom(arrayMinHeap, arrayMinHeap.size()-1);
    }

    @Override
    public ItemType getSmallest() {
        if(size() == 0){
            throw new IllegalArgumentException();
        }
        return arrayMinHeap.get(ROOT_IDX).item;
    }

    @Override
    public ItemType removeSmallest() {
        if(size() == 0){
            throw new NoSuchElementException();
        }

        return removeInNode(arrayMinHeap, ROOT_IDX).item;
    }

    @Override
    public void changePriority(ItemType item, double priority) {
        Integer findIdx = findNodeIdx(item);

        if(findIdx == null)
            throw new NoSuchElementException();

        if(arrayMinHeap.get(findIdx).priority == priority)
            return;

        Node modifyNode = arrayMinHeap.get(findIdx);
        modifyNode.priority = priority;

        if(greater(getParentIdx(findIdx), findIdx)){
            MinHeapifyFromBottom(arrayMinHeap, findIdx);
        }
        else{
            MinHeapifyFromTop(arrayMinHeap, findIdx);
        }
    }

    @Override
    public boolean contains(ItemType item) {
        return mapItemIdxInHeap.containsKey(item);
    }

    @Override
    public int size() {
        return arrayMinHeap.size()-1;
    }

    private void swap(ArrayList<Node> arrayHeap, int sourceIdx, int targetIdx){
        mapItemIdxInHeap.put(arrayHeap.get(sourceIdx).item, targetIdx);
        mapItemIdxInHeap.put(arrayHeap.get(targetIdx).item, sourceIdx);
        Collections.swap(arrayHeap, sourceIdx, targetIdx);
    }

    private int MinHeapifyFromBottom(ArrayList<Node> arrayHeap, int startIdx){
        while(startIdx > ROOT_IDX && greater(getParentIdx(startIdx), startIdx)){
            swap(arrayHeap, startIdx, getParentIdx(startIdx));
            startIdx = getParentIdx(startIdx);
        }
        return startIdx;
    }

    private int MinHeapifyFromTop(ArrayList<Node> arrayHeap, int startIdx){
        while(getLeftIdx(startIdx) < arrayHeap.size()){
            int swapIdx = getLeftIdx(startIdx);

            if(getRightIdx(startIdx) < arrayHeap.size() && greater(getLeftIdx(startIdx), getRightIdx(startIdx))){
                swapIdx = getRightIdx(startIdx);
            }

            if(greater(swapIdx, startIdx)){
                break;
            }

            swap(arrayHeap, swapIdx, startIdx);
            startIdx = swapIdx;
        }

        return startIdx;
    }

    private Node removeInNode(ArrayList<Node> arrayHeap, int removeIdx){
        swap(arrayHeap, removeIdx, arrayHeap.size()-1);
        Node removeNode = arrayMinHeap.remove(arrayMinHeap.size()-1);

        removeIdx = MinHeapifyFromBottom(arrayHeap, removeIdx);
        removeIdx = MinHeapifyFromTop(arrayHeap, removeIdx);

        mapItemIdxInHeap.remove(removeNode.item);
        return removeNode;
    }

    private Integer findNodeIdx(ItemType inputItem){
        return mapItemIdxInHeap.get(inputItem);
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

    private boolean greater(int i, int j){
        return arrayMinHeap.get(i).priority > arrayMinHeap.get(j).priority;
    }

    private class Node{
        double priority;
        ItemType item;

        public Node(ItemType item, double priority){
            this.item = item;
            this.priority = priority;
        }

        @Override
        public String toString() {
            return "(" + item.toString() + ", " + priority +")";
        }
    }

}
