import java.util.ArrayList;
import java.util.List;

public class BoardPuzzle implements IState<Integer[][],UsefulEnums.Direction>{

	private Integer[][] board;
	private BoardPuzzle parent;
	private UsefulEnums.Direction originDirection;
	private int depth;
    public BoardPuzzle(Integer[][] state, BoardPuzzle parent, UsefulEnums.Direction originDirection ) {
        this.board = state;
        this.parent = parent;
        this.originDirection = originDirection;
        if (parent == null) {
            this.depth = 0;
        } else {
            this.depth = this.parent.getDepth() + 1;
        }
    }
	@Override
	public Integer[][] getBoard() {
		// TODO Auto-generated method stub
		return this.board;
	}

	@Override
	public BoardPuzzle getParent() {
		// TODO Auto-generated method stub
		return this.parent;
	}

	@Override
	public UsefulEnums.Direction getOriginPath() {
		// TODO Auto-generated method stub
		return this.originDirection;
	}
	public int getDepth() {
		return this.depth;
	}
	
    private Integer[][] getBoardCopy() {
        Integer[][] boardCopy = new Integer[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                boardCopy[i][j] = board[i][j];
            }
        }
        return boardCopy;
    }

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

        List<UsefulEnums.Direction> validSteps = getValidDirectionForCurrentPuzzle(row,col);
        for (UsefulEnums.Direction direction : validSteps) {
            successors.add(developSons(getBoardCopy(), row, col, direction));
        }
        return successors;
    }
	
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
