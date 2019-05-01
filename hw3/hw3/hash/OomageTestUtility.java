package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /*
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */

        double[] numEachBucket = new double[M];

        double upperBound = (double) oomages.size() / 2.5;
        double lowerBound = (double) oomages.size() / 50.;

        for(Oomage oomage: oomages){
            int bucketIdx = (oomage.hashCode() & 0x7FFFFFFF) % M;
            numEachBucket[bucketIdx] += 1.;
        }

        for(double bucketNum: numEachBucket)
            if(bucketNum < lowerBound || bucketNum > upperBound)
                return false;

        return true;
    }
}
