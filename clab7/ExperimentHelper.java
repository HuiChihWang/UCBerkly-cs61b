import org.w3c.dom.Node;

/**
 * Created by hug.
 */
public class ExperimentHelper {

    /** Returns the internal path length for an optimum binary search tree of
     *  size N. Examples:
     *  N = 1, OIPL: 0
     *  N = 2, OIPL: 1
     *  N = 3, OIPL: 2
     *  N = 4, OIPL: 4
     *  N = 5, OIPL: 6
     *  N = 6, OIPL: 8
     *  N = 7, OIPL: 10
     *  N = 8, OIPL: 13
     */
    public static int optimalIPL(int N) {
        int optimalDepthSum = 0;
        int nodeCount = 1;
        while(nodeCount <= N){
            optimalDepthSum += (int) (Math.log((double) nodeCount)/ Math.log(2.));
            nodeCount += 1;
        }
        return optimalDepthSum;
    }

    /** Returns the average depth for nodes in an optimal BST of
     *  size N.
     *  Examples:
     *  N = 1, OAD: 0
     *  N = 5, OAD: 1.2
     *  N = 8, OAD: 1.625
     * @return
     */
    public static double optimalAverageDepth(int N) {
        return (double) optimalIPL(N) / (double) N;
    }

    public static void randomInsertionDoubleBST(BST<Double> BSTRoot, int treeSize){
        while(BSTRoot.size() < treeSize){
            Double randomGeneratedKey = GenerateRandomDouble();
            if(!BSTRoot.contains(randomGeneratedKey))
                BSTRoot.add(randomGeneratedKey);
        }
    }

    public static void randomDeletionInsertionDoubleBST(BST<Double> BSTRoot, String deletionApproch){
        if(!deletionApproch.equals("successor") && !deletionApproch.equals("random"))
            throw new IllegalArgumentException();

        Double key =  BSTRoot.getRandomKey();
        if(deletionApproch.equals("successor"))
            BSTRoot.deleteTakingSuccessor(key);
        if(deletionApproch.equals("random"))
            BSTRoot.deleteTakingRandom(key);

        while(true) {
            Double randomGeneratedKey = GenerateRandomDouble();
            if (!BSTRoot.contains(randomGeneratedKey)) {
                BSTRoot.add(randomGeneratedKey);
                break;
            }
        }
    }

    public static Double GenerateRandomDouble(){
        return Math.random();
    }

    public static void main(String[] args){
        for(int i = 1; i <= 10; ++ i)
            System.out.println(optimalIPL(i));

        System.out.println(optimalAverageDepth(1));
        System.out.println(optimalAverageDepth(5));
        System.out.println(optimalAverageDepth(8));

    }
}
