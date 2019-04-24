package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.HashMap;

public class Percolation {
    private WeightedQuickUnionUF UnionOfTopVirtualSite;
    private WeightedQuickUnionUF UnionOfBottomVirtualSite;
    private PercolationBoard StatesHolder;
    private int NumOfOpenSites;
    private int GridSize;
    private int VirtualSiteIndex;
    private boolean isPercolate;

    public Percolation(int N) {
        if (N <= 0)
            throw  new IllegalArgumentException();

        GridSize = N;
        NumOfOpenSites = 0;
        VirtualSiteIndex = GridSize * GridSize;
        StatesHolder = new PercolationBoard(GridSize);
        UnionOfTopVirtualSite = new WeightedQuickUnionUF(GridSize*GridSize + 1);
        UnionOfBottomVirtualSite = new WeightedQuickUnionUF(GridSize*GridSize +1);
    }

    public static void main(String[] args){

    }

    public void open(int row, int col){
        checkValidIndex(row, col);
        if(StatesHolder.GetState(row, col) == DataType.State.OPEN)
            return;

        ConnectedToSource(row, col);
        ConnectedToTarget(row, col);

        StatesHolder.SetState(DataType.State.OPEN, row, col);
        NumOfOpenSites += 1;

        ConnectToNeigbor(row, col);
        CheckIsPercolate(row, col);
    }

    public boolean isOpen(int row, int col){
        checkValidIndex(row, col);
        return StatesHolder.GetState(row, col) == DataType.State.OPEN;
    }

    public boolean isFull(int row, int col){
        checkValidIndex(row, col);
        return UnionOfTopVirtualSite.connected(VirtualSiteIndex, transformTo1DIndex(row, col));
    }

    public boolean isBlock(int row, int col){
        checkValidIndex(row, col);
        return StatesHolder.GetState(row, col) == DataType.State.BLOCK;
    }

    public int numberOfOpenSites(){
        return NumOfOpenSites;
    }

    public boolean percolates(){
        return isPercolate;
    }


    private void checkValidIndex(int row, int col){
        if(row >= GridSize || row < 0 || col >= GridSize || col < 0)
            throw new IndexOutOfBoundsException();
    }

    private int transformTo1DIndex(int row, int col){
        return row * GridSize + col;
    }

    private int[] getTranslationByDirection(DataType.Direction dir, int row, int col){
        if(dir == DataType.Direction.LEFT)
            return new int[]{row, col - 1};
        else if(dir == DataType.Direction.RIGHT)
            return new int[]{row, col + 1};
        else if(dir == DataType.Direction.TOP)
            return new int[]{row - 1, col};
        else if(dir == DataType.Direction.BOTTOM)
            return new int[]{row + 1, col};
        else
            throw new IllegalArgumentException();
    }

    private void ConnectToNeigbor(int row, int col){
        int current_index = transformTo1DIndex(row, col);
        HashMap<DataType.Direction, DataType.State> neighbors = StatesHolder.GetNeighborStates(row, col);
        for(DataType.Direction dir: neighbors.keySet()){
            if(neighbors.get(dir) == DataType.State.OPEN){
                int[] neigbor_pos = getTranslationByDirection(dir, row, col);
                int neigbor_index = transformTo1DIndex(neigbor_pos[0], neigbor_pos[1]);
                UnionOfTopVirtualSite.union(neigbor_index, current_index);
                UnionOfBottomVirtualSite.union(neigbor_index,current_index);
            }
        }
    }

    private void ConnectedToSource(int row, int col){
        if(row == 0)
            UnionOfTopVirtualSite.union(VirtualSiteIndex, transformTo1DIndex(row, col));
    }

    private void ConnectedToTarget(int row, int col){
        if(row == GridSize-1)
            UnionOfBottomVirtualSite.union(VirtualSiteIndex, transformTo1DIndex(row, col));
    }

    private void CheckIsPercolate(int row, int col){
        int current_idx = transformTo1DIndex(row, col);
        boolean isConnectToBottom = UnionOfBottomVirtualSite.connected(VirtualSiteIndex, current_idx);
        boolean isConnectToTop = UnionOfTopVirtualSite.connected(VirtualSiteIndex, current_idx);
        isPercolate = isConnectToBottom && isConnectToTop;
    }
}
