import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class A_Star extends AbstractSearchAlgo {
	
	private PriorityQueue<BoardPuzzle> openList;

    public A_Star(Integer[][] initialBoard) {
        this.currentBoard = new BoardPuzzle(initialBoard, null, null);
        this.openList = new PriorityQueue<>(new ManhettenDistance());
    }
	@Override
	public boolean runSearchAlgo() {
		
		openList.add(currentBoard);
        while (!this.openList.isEmpty()) {
            this.currentBoard = this.openList.remove();
            this.closedList.add(currentBoard);
            if (currentBoard.isGoal()) {
                this.cost = calculatePathCost(this.currentBoard);
                return true;
            }
            List<BoardPuzzle> sons = this.currentBoard.getSons();
            for (BoardPuzzle son : sons) {
                this.openList.add(son);
            }
        }
        return false;
	}
	
	//calculate the f(n).
    private int getCalculateFunc(BoardPuzzle state) {
        return  getManhettenDistancesSum(state);
    }
    
    private int getManhettenDistancesSum(BoardPuzzle state) {
        Integer[][] board = state.getBoard();
        int sumOfDistance = 0;
        //System.out.println(board.length);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
            	if((board[i][j] != 0) && (board[i][j] != i * board.length + j)) {
            	sumOfDistance += getManhettenDistance(board.length, board[i][j], i, j);
            	}
            }
        }
        return sumOfDistance;
    }
    
    private int getManhettenDistance(int size, int num, int row, int col) {
    	
        int targetX = (num - 1) / size; // expected x-coordinate (row)
        int targetY = (num - 1) % size; // expected y-coordinate (col)
        int dx = row - targetX; // x-distance to expected coordinate
        int dy = col - targetY; // y-distance to expected coordinate
        return Math.abs(dx) + Math.abs(dy); 
        // 1 0 3
        // 4 2 5
        // 7 8 6
        // 1 = 0
        // 2 = 1
        // 3 = 0
    	// 4 = 0
        // 5 = 1
        //
    }
    
    //func that calculate the cost of the path until goal.
    private int calculatePathCost(BoardPuzzle state) {
    	int cost = 0;
    	while (state != null) {
    		cost += getCalculateFunc(state);
    		state = state.getParent();
    	}
    	return cost;
    }
    public class ManhettenDistance implements Comparator<BoardPuzzle> {

        @Override
        public int compare(BoardPuzzle bp1, BoardPuzzle bp2) {
            return getCalculateFunc(bp1) - getCalculateFunc(bp2);
        }
    }

}
