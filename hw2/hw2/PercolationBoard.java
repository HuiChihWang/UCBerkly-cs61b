package hw2;

import java.util.Arrays;
import java.util.HashMap;

public class PercolationBoard {
    private DataType.State[][] StatesHolder;

    public PercolationBoard(int N){
        StatesHolder =  new DataType.State[N + 2][N + 2];
        for(DataType.State[] row : StatesHolder)
            Arrays.fill(row, DataType.State.BLOCK);
    }

    public void SetState(DataType.State state, int row, int col){
        StatesHolder[row + 1][col + 1] = state;
    }

    public DataType.State GetState(int row, int col){
        return StatesHolder[row + 1][col + 1];
    }

    public HashMap<DataType.Direction, DataType.State> GetNeighborStates(int row, int col) {
        HashMap<DataType.Direction, DataType.State> neigborState = new HashMap<>();
        neigborState.put(DataType.Direction.LEFT, StatesHolder[row+1][col]);
        neigborState.put(DataType.Direction.RIGHT, StatesHolder[row+1][col+2]);
        neigborState.put(DataType.Direction.TOP, StatesHolder[row][col+1]);
        neigborState.put(DataType.Direction.BOTTOM, StatesHolder[row+2][col+1]);
        return neigborState;
    }
}
