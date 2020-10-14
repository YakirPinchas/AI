import java.util.ArrayList;
import java.util.List;

public class BoardPuzzle implements IState<Integer[][],UsefulEnums.Direction>{

	private Integer[][] board;
	//parent = previous board
	private BoardPuzzle parent;
	//the sign that cause to parent change to current board
	private UsefulEnums.Direction originDirection;
	private int depth;
	private int orderExec;
    /**
     * Constructor of Board Puzzle
     * @param state is the current Board.
     * @param parent is the previous board
     * @param originDirection is the Sign that get the current board from parent board 
     */
    public BoardPuzzle(Integer[][] state, BoardPuzzle parent, UsefulEnums.Direction originDirection ) {
        this.board = state;
        this.parent = parent;
        this.originDirection = originDirection;
        //define the depth.
        if (parent == null) {
            this.depth = 0;
        } else {
            this.depth = this.parent.getDepth() + 1;
        }
    }
    /**
     * gets the current board
     * @return current board
     */
	@Override
	public Integer[][] getBoard() {
		// TODO Auto-generated method stub
		return this.board;
	}
    /**
     * gets the previous board
     * @return previous board(parent)
     */
	@Override
	public BoardPuzzle getParent() {
		// TODO Auto-generated method stub
		return this.parent;
	}
    /**
     * gets the sign that cause to parent to be current board
     * @return sign
     */
	@Override
	public UsefulEnums.Direction getOriginPath() {
		// TODO Auto-generated method stub
		return this.originDirection;
	}
	/**
	 * get the depth of the tree
	 * @return depth of the tree 
	 */
	public int getDepth() {
		return this.depth;
	}
    /**
     * returns order Execution
     * @return order Execution
     */
    public int getOrderExec() {
        return this.orderExec;
    }

    /**
     * sets the order Execution
     * @param order Execution
     */
    public void setOrderExec(int orderExec) {
        this.orderExec = orderExec;
    }
	/**
	 * get copy of the board in order to execute changes and don't lose the origin board
	 * @return copy of the board
	 */
    private Integer[][] getBoardCopy() {
        Integer[][] boardCopy = new Integer[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                boardCopy[i][j] = board[i][j];
            }
        }
        return boardCopy;
    }
    /**
     * compare between two board puzzle
     * @param backupPuzzle is the second board puzzle
     * @return true if the boards is equal , else - false.
     */
    public boolean compareBoardState(BoardPuzzle backupPuzzle) {
        Integer[][] otherBoard = backupPuzzle.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != otherBoard[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * gets the sons of current node , check if one of sons exist in tree.
     * @return list of sons of the current node
     */
    public List<BoardPuzzle> getSons() {
        List<BoardPuzzle> successors = new ArrayList<>();
        int row = 0, col = 0;
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].intValue() == 0) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }
        //use the func in order to found possible legal moves for the "0".
        List<UsefulEnums.Direction> validSteps = getValidDirectionForCurrentPuzzle(row,col);
        for (UsefulEnums.Direction direction : validSteps) {
        	//develop the all legal moves
            successors.add(developSons(getBoardCopy(), row, col, direction));
        }
        return successors;
    }
	/**
	 * function that check if possible move is legal or not.
	 * @param row is the row in board of current node
	 * @param col is the col in board of current node
	 * @return List of legal moves for this board
	 */
    private List<UsefulEnums.Direction> getValidDirectionForCurrentPuzzle(int row, int col) {
        List<UsefulEnums.Direction> validSteps = new ArrayList<>();
        if (row < board.length - 1) {
            validSteps.add(UsefulEnums.Direction.UP);
        }
        if (row > 0) {
            validSteps.add(UsefulEnums.Direction.DOWN);
        }
        if (col < board.length - 1) {
            validSteps.add(UsefulEnums.Direction.LEFT);
        }
        if (col > 0) {
            validSteps.add(UsefulEnums.Direction.RIGHT);
        }
        return validSteps;
    }
    /**
     * function that develop the sons of current board
     * @param boardCopy is copy of board in order to execute things
     * @param row is the row of board
     * @param col is the col of board
     * @param direction is the sign that need to execute in the board now
     * @return
     */
    private BoardPuzzle developSons(Integer[][] boardCopy, int row, int col, UsefulEnums.Direction direction) {
        switch (direction) {
            case UP:
                boardCopy[row][col] = boardCopy[row+1][col];
                boardCopy[row+1][col] = 0;
                break;
            case DOWN:
                boardCopy[row][col] = boardCopy[row-1][col];
                boardCopy[row-1][col] = 0;
                break;
            case LEFT:
                boardCopy[row][col] = boardCopy[row][col+1];
                boardCopy[row][col+1] = 0;
                break;
            case RIGHT:
                boardCopy[row][col] = boardCopy[row][col-1];
                boardCopy[row][col-1] = 0;
                break;
            default:
                break;
        }
        return new BoardPuzzle(boardCopy, this, direction);
    }
    /**
     * function that check if current board is the goal state
     * @return true - if yes , else - no.
     */
    public Boolean isGoal() {
        int count = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != count) {
                    if (i == board.length - 1 && j == board.length - 1 && board[i][j] == 0) {
                        continue;
                    }
                    return false;
                }
                count++;
            }
        }
        return true;
    }
}
