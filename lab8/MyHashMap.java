import java.util.*;

public class MyHashMap<KeyType, ValueType> implements Map61B<KeyType, ValueType> {

    private int numPairs;
    private int bucketSize;
    private int initialSize = 16;
    private double loadFactor = 0.75;

    private ArrayList<Pair>[] bucketList;
    private Set<KeyType> keySet;

    public MyHashMap(){
        InitialSetUp();
    }

    public MyHashMap(int initialSize){
        this.initialSize = initialSize;
        InitialSetUp();
    }

    public MyHashMap(int initialSize, double loadFactor){
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        InitialSetUp();
    }

    @Override
    public void clear(){
        numPairs = 0;
        InitialSetUp();
    }

    @Override
    public void put(KeyType key, ValueType value) {
        Pair findPair = GetPair(key);
        if(findPair != null) {
            findPair.value = value;
            return;
        }

        Pair item = CreatePair(key, value);
        putItemIntoBucket(item, bucketList);
        keySet.add(key);
        numPairs += 1;

        if(GetPairBucketRatio() >= loadFactor)
            ResizeMap();
    }

    @Override
    public boolean containsKey(KeyType key) {
        return GetPair(key) != null;
    }

    @Override
    public int size() {
        return numPairs;
    }

    @Override
    public Set<KeyType> keySet() {
        Set<KeyType> set = new HashSet<>();
        for(KeyType key: this) {
            set.add(key);
        }
        return set;
    }

    @Override
    public ValueType get(KeyType key) {
        Pair findPair = GetPair(key);
        if(findPair != null)
            return findPair.value;
        return null;
    }

    @Override
    public Iterator<KeyType> iterator() {
        return new MyHashMapIterator();
    }

    @Override
    public ValueType remove(KeyType key) {
        Pair item = GetPair(key);
        if(item == null)
            return null;

        int bucketIndex = HashModFunc(key.hashCode(), bucketSize);
        bucketList[bucketIndex].remove(item);

        if(bucketList[bucketIndex].isEmpty())
            bucketList[bucketIndex] = null;

        keySet.remove(key);
        numPairs -= 1;

        return item.value;
    }

    @Override
    public ValueType remove(KeyType key, ValueType value){
        Pair item = GetPair(key);
        if(item == null || item.value != value)
            return null;

        return remove(key);
    }


    private class Pair {
        public KeyType key;
        public ValueType value;

        public Pair(KeyType key, ValueType value){
            this.key = key;
            this.value = value;
        }
    }

    private void InitialSetUp(){
        bucketSize = initialSize;
        bucketList = CreateArrayListArray(bucketSize);
        keySet = CreateHashSet();
    }
    private ArrayList<Pair>[] CreateArrayListArray(int arraySize){
        return new ArrayList[arraySize];
    }

    private ArrayList<Pair> CreateArrayList(){
        return new ArrayList<>();
    }

    private Set<KeyType> CreateHashSet(){
        return new HashSet<>();
    }

    private Pair CreatePair(KeyType key, ValueType value){
        return new Pair(key, value);
    }

    private void ResizeMap(){
        ArrayList<Pair>[] resizedBucket = CreateArrayListArray(2*bucketSize);

        for(KeyType key: keySet){
            Pair item = CreatePair(key, get(key));
            putItemIntoBucket(item, resizedBucket);
        }

        bucketList = resizedBucket;
        bucketSize *= 2;
    }

    private double GetPairBucketRatio(){
        return (double) numPairs / (double) bucketSize;
    }

    private void putItemIntoBucket(Pair item, List<Pair>[] bucketArray){
        int bucketIdx = HashModFunc(item.key.hashCode(), bucketArray.length);

        if(CheckNullBucket(bucketArray, bucketIdx))
            bucketArray[bucketIdx] = CreateArrayList();

        bucketArray[bucketIdx].add(item);
    }

    private int HashModFunc(int hashcode, int size){
        return Math.floorMod(hashcode, size);
    }

    private Pair GetPair(KeyType key){
        int bucketIdx = Math.floorMod(key.hashCode(), bucketSize);
        if(!CheckNullBucket(bucketList, bucketIdx))
            for(Pair item: bucketList[bucketIdx])
                if(item.key.equals(key))
                    return item;
        return null;
    }


    private boolean CheckNullBucket(List<Pair>[] bucketListArray, int bucketIdx){
        return bucketListArray[bucketIdx] == null;
    }

    private class MyHashMapIterator implements Iterator<KeyType>{
        int visitCount;
        int bucketCount;
        ListIterator<Pair> eachBucketIter;

        MyHashMapIterator(){
            visitCount = 0;
            bucketCount = 0;
            InitializeBucketIterator();
        }

        @Override
        public boolean hasNext() {
            return visitCount < numPairs;
        }

        @Override
        public KeyType next() {
            KeyType key = eachBucketIter.next().key;
            visitCount += 1;

            if(IsBucketEnded()){
                bucketCount += 1;
                InitializeBucketIterator();
            }

            return key;
        }

        private void InitializeBucketIterator(){
            while(CheckNullBucket(bucketList, bucketCount)) {
                bucketCount += 1;

                if(bucketCount >= bucketList.length)
                    return;
            }
            eachBucketIter = bucketList[bucketCount].listIterator();
        }

        private boolean IsBucketEnded(){
            return !eachBucketIter.hasNext();
        }
    }
}
