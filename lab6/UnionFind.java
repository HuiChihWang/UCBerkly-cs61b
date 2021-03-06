import java.util.ArrayList;
import java.util.List;

public class UnionFind {
    private int[] parentsArray;

    public int[] getParentsArray(){
        return parentsArray;
    }

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        parentsArray = new int[n];

        for(int i = 0; i < n; ++i){
            parentsArray[i] = -1;
        }

    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex < 0 || vertex >= parentsArray.length)
            throw new IllegalArgumentException("Invalid Vertex Index!!");
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
        return -parent(find(v1));

    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        return parentsArray[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);

        if(find(v1) == find(v2))
            return true;

        return false;
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);

        int root1 = find(v1);
        int root2 = find(v2);

        if(root1==root2)
            return;

        if(sizeOf(root1) > sizeOf(root2)) {
            parentsArray[root1] += parentsArray[root2];
            parentsArray[root2] = root1;
        }
        else{
            parentsArray[root2] += parentsArray[root1];
            parentsArray[root1] = root2;
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        int root = vertex;
        List<Integer> visitVertexs = new ArrayList<>();

        while(parentsArray[root] >= 0){
            visitVertexs.add(root);
            root = parentsArray[root];
        }

        //path compression
        for(int visitVertex: visitVertexs){
            parentsArray[visitVertex] = root;
        }
        return root;
    }

    public int[] getSet(int vertex){
        validate(vertex);
        int[] set = new int[sizeOf(vertex)];
        int count = 0;
        for(int i = 0; i < parentsArray.length; ++i)
            if(connected(vertex, i)){
                set[count] = i;
                count += 1;
            }
        return set;
    }

}
