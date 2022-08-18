package DataStructures;

import java.util.ArrayList;
import java.util.LinkedList;

/***
 * HashMap implements
 *
 * Generic Param's
 * @param <K> For Key
 * @param <V> For Value
 */
public class HashMap <K,V>{
    public class HMNode{ // Pair of HashMap of storing key and value
        K key;
        V value;

        public HMNode(K key,V value){
            this.key = key;
            this.value = value;
        }
    }
    private int size;
    private LinkedList<HMNode>[] buckets;

    public HashMap() {
        initBuckets(4);
        size = 0;
    }

    private void initBuckets(int N) { // Adding LinkedList inside each bucket
        buckets = new LinkedList[N];
        for (int eachBucket = 0; eachBucket < buckets.length; eachBucket++) {
            buckets[eachBucket] = new LinkedList<>();
        }
    }

    public void put(K key, V value) throws Exception {
        int bucketIndex = hashingFunction(key);
        int dataIndex = findKeyInsideTheBucket(key,bucketIndex);

        if(dataIndex == -1){
            // Insert
            HMNode newNode = new HMNode(key,value);
            buckets[bucketIndex].add(newNode);
            size++;
        }
        else{
            // Update
            HMNode updateNode = buckets[bucketIndex].get(dataIndex);
            updateNode.value = value;
            buckets[bucketIndex].set(dataIndex,updateNode);
        }

        // For Rehashing later we look
        double lambda = size * 1.0 / buckets.length;
        if(lambda > 2.0){
            rehashingFunction(); // Means increasing the bucket capacity
        }
    }

    private void rehashingFunction() throws Exception{
        LinkedList<HMNode>[] oldBucketData = buckets; // Retrieve all old buckkets data

        initBuckets(buckets.length * 2);// bucket capacity initialise again with more spaces
        size = 0;// making bucket size initial zero

        // Previous data added inside the new bucket
        for (int i = 0; i < oldBucketData.length; i++) {
            for(HMNode node : oldBucketData[i]){
                put(node.key,node.value);//adding the data inside new bucket
            }
        }

    }

    public int hashingFunction(K key){
        int hashingCode = key.hashCode(); // Every int, string, bool hasCode
        return Math.abs(hashingCode) % buckets.length;
    }

    public int findKeyInsideTheBucket(K key,int bucketIndex){
        int currentIndex = 0;
        for(HMNode node : buckets[bucketIndex]){
            if(node.key.equals(key)){
                return currentIndex;
            }
            currentIndex++;
        }

        return -1;
    }

    public V get(K key) throws Exception {
        int bucketIndex = hashingFunction(key);
        int dataIndex = findKeyInsideTheBucket(key,bucketIndex);

        if(dataIndex == -1){
            // Key is not present
            return null;
        }
        else{
            // Key is present
            HMNode node = buckets[bucketIndex].get(dataIndex);
            return node.value;
        }
    }

    public boolean containsKey(K key) {
        int bucketIndex = hashingFunction(key);
        int dataIndex = findKeyInsideTheBucket(key,bucketIndex);

        if(dataIndex == -1){
            // Key is not present
            return false;
        }
        else{
            // Key is present
            return true;
        }
    }

    public V remove(K key) throws Exception {
        int bucketIndex = hashingFunction(key);
        int dataIndex = findKeyInsideTheBucket(key,bucketIndex);

        if(dataIndex == -1){
            // Key is not present
            return null;
        }
        else{
            // Key is present
            HMNode returningValue = buckets[bucketIndex].remove(dataIndex);
            size--;
            return returningValue.value;
        }
    }

    public ArrayList<K> keySet() throws Exception {
        ArrayList<K> allData = new ArrayList<>();

        for (int i = 0; i <buckets.length ; i++) {
            for(HMNode node : buckets[i]){
                allData.add(node.key);
            }
        }

        return allData;
    }

    public int size() {
        return size;
    }

    public void display() {
        System.out.println("Display HashMap");
        for (int eachBucket = 0; eachBucket < buckets.length; eachBucket++) {
            System.out.print("Bucket" + eachBucket + " ");
            for (HMNode node : buckets[eachBucket]) {
                System.out.print( node.key + "@" + node.value + " ");
            }
            System.out.println(".");
        }
        System.out.println("-----------------");
    }

}