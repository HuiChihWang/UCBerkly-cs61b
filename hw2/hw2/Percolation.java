package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.HashMap;

public class Percolation {
    private WeightedQuickUnionUF UnionOfOpenSites;
    private PercolationBoard StatesHolder;
    private int NumOfOpenSites;
    private int GridSize;
    private int VirtualSourceIndex;
    private int VirtualTargetIndex;

    public Percolation(int N) {
        if (N <= 0)
            throw  new IllegalArgumentException();

        GridSize = N;
        NumOfOpenSites = 0;
        VirtualSourceIndex = GridSize * GridSize;
        VirtualTargetIndex = GridSize * GridSize + 1;
        StatesHolder = new PercolationBoard(GridSize);
        UnionOfOpenSites = new WeightedQuickUnionUF(GridSize*GridSize + 2);
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
        ConnectToNeigbor(row, col);
        NumOfOpenSites += 1;
    }

    public boolean isOpen(int row, int col){
        checkValidIndex(row, col);
        return StatesHolder.GetState(row, col) == DataType.State.OPEN;
    }

    public boolean isFull(int row, int col){
        checkValidIndex(row, col);
        boolean isFullCheckByUnion = UnionOfOpenSites.connected(VirtualSourceIndex, transformTo1DIndex(row,col));
        boolean isFullCheckByBottom = CheckBottomFull(row,col);
        return isFullCheckByUnion && isFullCheckByBottom;
    }

    public boolean isBlock(int row, int col){
        checkValidIndex(row, col);
        return StatesHolder.GetState(row, col) == DataType.State.BLOCK;
    }

    public int numberOfOpenSites(){
        return NumOfOpenSites;
    }

    public boolean percolates(){
        return UnionOfOpenSites.connected(VirtualSourceIndex, VirtualTargetIndex);
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
                UnionOfOpenSites.union(neigbor_index, current_index);
            }
        }

    }

    private void ConnectedToSource(int row, int col){
        if(row == 0)
            UnionOfOpenSites.union(VirtualSourceIndex, transformTo1DIndex(row, col));
    }

    private void ConnectedToTarget(int row, int col){
        if(row == GridSize-1)
            UnionOfOpenSites.union(VirtualTargetIndex, transformTo1DIndex(row, col));
    }

    private boolean CheckBottomFull(int row, int col){

        if(percolates() && row == GridSize - 1){
            boolean isLeftConnect = IsBelongToSameSet(row, col, DataType.Direction.LEFT);
            boolean isRightConnect = IsBelongToSameSet(row, col, DataType.Direction.RIGHT);
            boolean isTopConnect = IsBelongToSameSet(row, col, DataType.Direction.TOP);

            return isLeftConnect || isRightConnect || isTopConnect;
        }
        return true;
    }

    private boolean IsBelongToSameSet(int row, int col, DataType.Direction dir){
        int[] shift_point  = getTranslationByDirection(dir, row, col);
        int row_shift = shift_point[0];
        int col_shift = shift_point[1];

        if(IsPointInBound(row_shift, col_shift)){
            int currentIdx = transformTo1DIndex(row,col);
            int neighborIdx = transformTo1DIndex(row_shift,col_shift);
            return UnionOfOpenSites.connected(currentIdx, neighborIdx);
        }

        return false;
    }

    private boolean IsPointInBound(int row, int col){
        return row >=0 && row <= GridSize-1 && col >= 0 && col <= GridSize-1;
    }
}
