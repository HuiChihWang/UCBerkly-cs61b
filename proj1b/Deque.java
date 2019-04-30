public interface Deque<T>{

    /* Deque Basic Operation API */
    public void addFirst(T item);
    public void addLast(T item);

    public T removeFirst();
    public T removeLast();

    /* Access Deque API*/
    public T get(int index);

    /* Print Deque API */
    public void printDeque();


    /* Deque Size API */
    public int size();
    default public boolean isEmpty(){
        return size() == 0;
    }

}