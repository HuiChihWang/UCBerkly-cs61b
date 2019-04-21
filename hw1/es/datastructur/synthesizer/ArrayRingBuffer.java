package es.datastructur.synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T>{
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] bufferArray;

    private void ResetIndex(){
        first = first % capacity();
        last = last % capacity();
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        bufferArray = (T[]) new Object[capacity];
        first = last = 0;
        fillCount = 0;

    }


    @Override
    public int capacity(){
        return bufferArray.length;
    }

    @Override
    public int fillCount(){
        return fillCount;
    }
    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if(isFull())
            throw new RuntimeException("Ring buffer overflow");


        bufferArray[last] = x;
        last += 1;
        fillCount += 1;
        ResetIndex();
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and
        //       update first.
        if(isEmpty())
            throw new RuntimeException("Ring buffer underflow");

        T x = bufferArray[first];
        first += 1;
        fillCount -= 1;
        ResetIndex();
        return x;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        return bufferArray[first];
    }


    @Override
    public  Iterator<T> iterator(){
        return new ArrayRingBufferIterator<T>();
    }

    private class ArrayRingBufferIterator<T> implements Iterator<T>{
        private int arrayIndex;
        private int visitCount;

        public ArrayRingBufferIterator(){
            arrayIndex = first;
            visitCount = 0;
        }

        @Override
        public boolean hasNext(){

            return visitCount < fillCount;
        }

        @Override
        public T next(){
            T item = (T) bufferArray[arrayIndex];
            arrayIndex += 1;
            visitCount += 1;
            ResetArrayIndex();
            return item;
        }

        private void ResetArrayIndex(){
            arrayIndex = arrayIndex % bufferArray.length;
        }
    }

    @Override
    public String toString(){
        String out = "{";
        for(T item: this){
            out += item.toString();
            out += ", ";
        }
        out += "}";
        return out;
    }

    @Override
    public boolean equals(Object o){
        if(o == null)
            return false;

        if(this.getClass() != o.getClass())
            return false;

        ArrayRingBuffer<T> other = (ArrayRingBuffer<T>) o;

        if(other.fillCount() != this.fillCount())
            return false;

        Iterator<T> it = iterator();
        for(T item: other){
            if(it.next() != item)
                return false;
        }
        return true;
    }


}

