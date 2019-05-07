package bearmaps;


import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import java.util.NoSuchElementException;


public class ArrayHeapMinPQ<ItemType> implements ExtrinsicMinPQ<ItemType> {

    private ArrayList<Node<ItemType>> arrayMinHeap;
    private HashSet<ItemType> setItem;
    private int ROOT_IDX = 1;

    public ArrayHeapMinPQ(){
        setItem =  new HashSet<>();
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
        setItem.add(item);
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

        ItemType removeSmallItem = removeInNode(arrayMinHeap, ROOT_IDX).GetItem();
        return removeSmallItem;
    }

    @Override
    public void changePriority(ItemType item, double priority) {
        Integer findIdx = findNodeIdx(arrayMinHeap, item, ROOT_IDX);
        if(findIdx == null)
            throw new NoSuchElementException();

        if(arrayMinHeap.get(findIdx).GetPriority() == priority)
            return;

        Node<ItemType> modifyNode = removeInNode(arrayMinHeap, findIdx);

        modifyNode.SetPriority(priority);
        add(item, priority);
    }

    @Override
    public boolean contains(ItemType item) {
        return setItem.contains(item);
    }

    @Override
    public int size() {
        return arrayMinHeap.size()-1;
    }

    public void printHeap(){
        PrintHeapDemo.printSimpleArrayListHeap(arrayMinHeap);
    }

    private int MinHeapifyFromBottom(ArrayList<Node<ItemType>> arrayHeap, int startIdx){
        while(startIdx > ROOT_IDX && greater(getParentIdx(startIdx), startIdx)){
            Collections.swap(arrayHeap, startIdx, getParentIdx(startIdx));
            startIdx = getParentIdx(startIdx);
        }
        return startIdx;
    }

    private int MinHeapifyFromTop(ArrayList<Node<ItemType>> arrayHeap, int startIdx){
        while(getLeftIdx(startIdx) < arrayHeap.size()){
            int swapIdx = getLeftIdx(startIdx);

            if(getRightIdx(startIdx) < arrayHeap.size() && greater(getLeftIdx(startIdx), getRightIdx(startIdx))){
                swapIdx = getRightIdx(startIdx);
            }

            if(greater(swapIdx, startIdx)){
                break;
            }

            Collections.swap(arrayHeap, swapIdx, startIdx);
            startIdx = swapIdx;
        }

        return startIdx;
    }

    private Node<ItemType> removeInNode(ArrayList<Node<ItemType>> arrayHeap, int removeIdx){
        Collections.swap(arrayHeap, removeIdx, arrayHeap.size()-1);
        Node<ItemType> removeNode = arrayMinHeap.remove(arrayMinHeap.size()-1);

        removeIdx = MinHeapifyFromBottom(arrayHeap, removeIdx);
        removeIdx = MinHeapifyFromTop(arrayHeap, removeIdx);

        setItem.remove(removeNode.GetItem());
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

    private boolean greater(int i, int j){
        return arrayMinHeap.get(i).GetPriority() > arrayMinHeap.get(j).GetPriority();
    }

}
