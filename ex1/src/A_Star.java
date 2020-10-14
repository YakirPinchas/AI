import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
/**
 * the A_STAR algorithm
 */
public class A_Star extends AbstractSearchAlgo {
	//in A STAR we use in priority Queue
	private PriorityQueue<BoardPuzzle> openList;
    /**
     * Constructor of A_STAR
     * @param init board is the initial board that we get from the input file
     */
    public A_Star(Integer[][] initialBoard) {
        this.currentBoard = new BoardPuzzle(initialBoard, null, null);
        this.openList = new PriorityQueue<>(new ManhettenDistance());
    }
    /**
     * run the algorithm of A STAR
     * @return true if we came to Goal state , else - false.
     */
	@Override
	public boolean runSearchAlgo() {
		int orderExec = 0;
		this.currentBoard.setOrderExec(orderExec);
		orderExec++;
		this.openList.add(currentBoard);
		while (!this.openList.isEmpty()) {
            this.currentBoard = this.openList.remove();
          //  System.out.println(closedList.size());
            this.closedList.add(currentBoard);
            if (currentBoard.isGoal()) {
            	//calculate the cost of path to goal board.
            	this.cost = calculatePathCost(this.currentBoard);
                return true;
            }
            List<BoardPuzzle> sons = this.currentBoard.getSons();
            for (BoardPuzzle son : sons) {
            	son.setOrderExec(orderExec);
                this.openList.add(son);
                orderExec++;
            }
            
        }
        return false;
	}
	/**
	 * function that calculate of f(n) + g(n) = distance Manhattan and depth of the board
	 * @param state is the current board
	 * @return the calculate of f(n) + g(n)
	 */
    private int getCalculateFunc(BoardPuzzle state) {
        return  state.getDepth() + getManhettenDistancesSum(state);
    }
    /**
     * function that calculate the sum of distance Manhattan bettwen two boards
     * @param state is the current board
     * @return the sum of Manhattan Distance
     */
    private int getManhettenDistancesSum(BoardPuzzle state) {
        Integer[][] board = state.getBoard();
        int sumOfDistance = 0;
        //System.out.println(board.length);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
            	//we are not touch in cell of 0
            	if(board[i][j] != 0) {
            	sumOfDistance += getManhettenDistance(board.length, board[i][j], i, j);
            	}
            }
        }
        return sumOfDistance;
    }
    /**
     * function that calculate the Manhattan distance between current board to goal board
     * @param size the size of the board
     * @param num is the value board[row][col]
     * @param row is the current row of the board
     * @param col is the current col of the board
     * @return the distance current board to goal board
     */
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
    
    /**
     * function that calculate the cost of the path from initial board to goal board
     * @param state is the current board
     * @return
     */
    private int calculatePathCost(BoardPuzzle state) {
    	
    	int cost = -1;
    	while (state != null) {
    		//cost of every move in board is 1.
    		cost++;
    		//move to previous board.
    		state = state.getParent();
    	}
    	return cost;
    }
    /**
     * class of Manhattan Distance with comparator in order to compare between two situations
     * @author Yakir pinchas 18/11/2018
     *
     */
    public class ManhettenDistance implements Comparator<BoardPuzzle> {

        @Override
        public int compare(BoardPuzzle bp1, BoardPuzzle bp2) {
           if (getCalculateFunc(bp1) != getCalculateFunc(bp2)) {
        	   return getCalculateFunc(bp1) - getCalculateFunc(bp2);
           } else {
        	   if(bp1.getOrderExec() < bp2.getOrderExec()) {
        		   return -1;
        	   }
        	   return 1;
           }
        }
    }

}
