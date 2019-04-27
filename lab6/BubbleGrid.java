import java.util.Arrays;

public class BubbleGrid {
    private int[][] stateHolder;
    private UnionFind UnionBubble;
    private int height;
    private int width;
    private int unionSize;
    private int ceilVertexIdx;
    private int BubbleNum;

    private enum Direction{
        LEFT, BOTTOM, RIGHT, TOP
    }

    public static BubbleGrid CreateBubbleGrid(int[][] grid){
        return new BubbleGrid(grid);
    }

    public BubbleGrid(int[][] grid){
        height = grid.length;
        width = grid[0].length;
        unionSize = height * width + 1;
        ceilVertexIdx = unionSize - 1;
        stateHolder = grid;
        CalculateBubbleNum();
    }

    public int[] popBubbles(int[][] darts){
        int[] numBubbleFall = new int[darts.length];
        for(int timeIdx = 0; timeIdx < darts.length; ++timeIdx) {
            numBubbleFall[timeIdx] = popBubblesPerDart(darts[timeIdx][0], darts[timeIdx][1]);
        }
        return numBubbleFall;
    }

    public int popBubblesPerDart(int row, int col){
        validatePos(row, col);

        if(!changeState(row, col))
            return 0;
        ConnectUnion();
        int BubbleFall = BubbleNum - GetBubbleNumCoonectedCeil();
        UpdateState();
        return BubbleFall;
    }

    public int GetBubbleNum(){
        return BubbleNum;
    }

    private void CalculateBubbleNum(){
        BubbleNum = 0;
        for(int rowIdx = 0; rowIdx < height; ++rowIdx)
            for(int colIdx = 0; colIdx < width; ++colIdx)
                if(hasBubble(rowIdx, colIdx))
                    ++BubbleNum;
    }

    private void ConnectUnion(){
        UnionBubble = CreateUnion(unionSize);
        connectCeil();

        for(int rowIdx = 1; rowIdx < height; ++rowIdx)
            for(int colIdx = 0; colIdx < width; ++colIdx){
                connectNeighbors(rowIdx, colIdx);
            }
    }

    private void UpdateState(){
        for(int rowIdx = 0; rowIdx < height; ++rowIdx)
            for(int colIdx = 0; colIdx < width; ++colIdx){
                boolean isConnectCeil = UnionBubble.connected(ceilVertexIdx, getIndex(rowIdx, colIdx));
                if(!isConnectCeil){
                    changeState(rowIdx, colIdx);
                }
            }
    }

    private boolean hasBubble(int row, int col){
        return stateHolder[row][col] == 1;
    }
    
    private int getIndex(int row, int col){
        return row * width + col;
    }
    
    private boolean changeState(int row, int col){
        if(hasBubble(row, col)) {
            stateHolder[row][col] = 0;
            BubbleNum -= 1;
            return true;
        }
        return false;
    }
    
    private void connectCeil(){
        for(int colIdx = 0; colIdx < width; ++colIdx)
            if(hasBubble(0, colIdx))
                UnionBubble.union(getIndex(0,colIdx), ceilVertexIdx);
    }

    private void connectNeighbors(int row, int col){
        if(hasBubble(row, col))
            for(Direction dir: Direction.values())
                connectNeighborToDir(row, col, dir);
    }

    private void connectNeighborToDir(int row, int col, Direction dir){
        int[] neighborPos = getNeighborPos(row, col, dir);
        int neighborRow = neighborPos[0];
        int neighborCol = neighborPos[1];
        if(checkInBound(neighborRow, neighborCol) && hasBubble(neighborRow, neighborCol)) {
            UnionBubble.union(getIndex(neighborRow, neighborCol), getIndex(row, col));
        }
    }

    private int[] getNeighborPos(int row, int col, Direction dir){
        if(dir == Direction.LEFT)
            return new int[]{row, col - 1};
        else if(dir == Direction.RIGHT)
            return new int[]{row, col + 1};
        else if(dir == Direction.TOP)
            return new int[]{row - 1, col};
        else if(dir == Direction.BOTTOM)
            return new int[]{row + 1, col};
        else
            throw new IllegalArgumentException();
    }

    private boolean checkInBound(int row, int col){
        return row >=0 && row < height && col >=0 && col < width;
    }

    private int GetBubbleNumCoonectedCeil(){
        return UnionBubble.sizeOf(ceilVertexIdx) - 1;
    }

    private UnionFind CreateUnion(int N){
        return new UnionFind(N);
    }

    private void validatePos(int row, int col){
        if(!checkInBound(row, col))
            throw new IndexOutOfBoundsException();
    }



        
}
