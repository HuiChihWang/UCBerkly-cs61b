public class LinkedListDeque<T> {

    private class Node{
        public T item;
        public Node next;
        public Node prev;

        public Node(T in){
            item = in;
            next = null;
            prev = null;
        }

        public Node(){
            next = null;
            prev = null;
        }
    }


    private int length;
    private Node head;

    /* Constructor */
    public LinkedListDeque(){
        length = 0;
        head = new Node();
        head.next = head;
        head.prev = head;
    }

    public LinkedListDeque(LinkedListDeque other){
        length = 0;
        head = new Node();
        head.next = head;
        head.prev = head;

        for(int i = 0; i< other.size(); ++ i){
            addLast((T) other.get(i));
        }



    }

    /* Deque API*/
    public void addFirst(T item){
        Node item_node = new Node(item);
        item_node.next = head.next;
        item_node.prev = head;
        head.next.prev = item_node;
        head.next = item_node;
        length += 1;

    }

    public void addLast(T item){
        Node item_node = new Node(item);
        item_node.next = head;
        item_node.prev = head.prev;
        head.prev.next = item_node;
        head.prev = item_node;

        length += 1;
    }

    public boolean isEmpty(){
        return length==0;
    }

    public int size(){
        return length;

    }

    public void printDeque(){
        Node ptr = head.next;
        while(ptr != head){
            System.out.print(ptr.item);
            System.out.print(" ");
            ptr = ptr.next;
        }
        System.out.printf("\n");
    }

    public T removeFirst(){
        if (isEmpty())
            return null;

        Node remove_item = head.next;
        head.next = remove_item.next;
        remove_item.next.prev = head;

        length -= 1;

        return remove_item.item;
    }


    public T removeLast(){
        if (isEmpty())
            return null;

        Node remove_item = head.prev;
        head.prev = remove_item.prev;
        remove_item.prev.next = head;

        length -= 1;

        return remove_item.item;
    }

    public T get(int index){
        Node ptr = head.next;
        int count = 0;
        while(ptr != head){
            if (count == index)
                return ptr.item;
            ptr = ptr.next;
            count += 1;
        }
        return null;
    }

    public T getRecursive(int index){

        if(index<0 || index > size()-1)
            return null;

        return getNodeRecursive(index).item;
    }

    private Node getNodeRecursive(int index){
        if(index == 0){
            return head.next;
        }
        return getNodeRecursive(index-1).next;
    }




}
