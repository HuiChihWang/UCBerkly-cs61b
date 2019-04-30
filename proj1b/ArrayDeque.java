import java.util.Arrays;
import java.util.Objects;

public class ArrayDeque<T> implements Deque<T> {

    private T[] items;
    private  int size;
    private int head;
    private int tail;

    /* Constructor */
    public ArrayDeque(){
        items = (T []) new Object[8];
        size = 0;
        head = 0;
        tail = -1;

    }

    public ArrayDeque(ArrayDeque other){
        head = 0;
        tail = -1;
        size = 0;
        items = (T []) new Object[8];

        for(int i = 0; i < other.size(); ++i)
            addLast((T) other.get(i));

    }

    /* helper function */
    private void resize(int capacity){
       T[] a = (T []) new Object[capacity];
       int head_circle = circle_idx(head);
       int tail_circle = circle_idx(tail);

       if(head_circle > tail_circle){
           System.arraycopy(items, head_circle, a, 0, items.length - head_circle);
           System.arraycopy(items, 0 , a, items.length - head_circle, tail_circle + 1);
       }
       else{
           System.arraycopy(items, head_circle, a, 0, tail_circle-head_circle+1);
       }


       head = 0;
       tail = size - 1;
       items = a;
    }

    public  boolean isFull(){
        return items.length == size;
    }

    private int circle_idx(int index){
        return Math.floorMod(index, items.length);
    }

    private boolean isNeedCut(){
        return ((double)size / items.length <= 0.25) && items.length >= 16 ;
    }


    /* Deque API */
    @Override
    public void addFirst(T item){
        // check whether array full
        if(isFull())
            resize(items.length * 2);

        head -= 1;
        items[circle_idx(head)] = item;
        size += 1;


    }
    @Override
    public void addLast(T item){
        // check whether array full
        if(isFull()) {
            resize(items.length * 2);
        }
        tail += 1;
        items[circle_idx(tail)] = item;
        size += 1;

    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque(){
        for (int idx = head; idx <= tail; ++ idx){
            System.out.print(items[circle_idx(idx)]);
            System.out.print(" ");
        }
        System.out.print("\n");

    }

    @Override
    public T removeFirst(){
        // check isEmpty()
        if(isEmpty())
            return null;

        T item_rm = items[circle_idx(head)];

        head += 1;
        size -= 1;

        // check whether need downsize array
        if(isNeedCut())
            resize(items.length/2);

        return item_rm;

    }

    @Override
    public T removeLast(){
        // check isEmpty()
        if(isEmpty())
            return null;

        T item_rm = items[circle_idx(tail)];

        tail -= 1;
        size -= 1;

        // check whether need downsize array
        if(isNeedCut()) {
            resize(items.length / 2);
        }
        return item_rm;

    }

    @Override
    public T get(int index){
        if (index < 0 || index >=size)
            return null;
        return items[circle_idx(head+index)];
    }


    public static void main(String[] args){
        ArrayDeque<Integer> dq = new ArrayDeque<>();

        // add test
        dq.addFirst(5);
        dq.addLast(10);
        dq.addLast(6);
        dq.addFirst(7);
        dq.addLast(33);
        dq.addFirst(10);
        dq.addFirst(1);
        dq.addFirst(2);
        dq.printDeque();

//        System.out.println(dq.isFull());
//        System.out.println(dq.size());
//        System.out.println(dq.get(5));
//        System.out.println(dq.get(10));

        // resize test
        dq.addLast(3);
        for(int i=0; i<7;++i)
            dq.addFirst(i);
        dq.printDeque();


//        // remove test
//        for(int i=0; i< 10; ++i) {
//            System.out.println(dq.removeFirst());
//            System.out.println(dq.removeLast());
//        }

        dq.printDeque();

        // test copy constructor
        System.out.println("Test copy constructor");
        ArrayDeque <Integer> dq_copy = new ArrayDeque<Integer>(dq);
        dq.printDeque();
        dq_copy.addLast(10);
        dq_copy.printDeque();




    }
}
