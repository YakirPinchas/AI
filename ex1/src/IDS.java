import java.util.List;
import java.util.Stack;
/**
 * the IDS algorithm
 */
public class IDS extends AbstractSearchAlgo{
    
	private Stack<BoardPuzzle> openList;
    /**
     * Constructor of IDS.
     * @param init board is the initial board that we get from the input file
     */
    public IDS(Integer[][] initBoard) {
        this.currentBoard = new BoardPuzzle(initBoard, null, null);
        //we use in Stack in order to implement IDS.
        this.openList = new Stack<>();
        //the cost of IDS = 0.
        this.cost = 0;
    }
    /**
     * run the algorithm of IDS
     * @return true if we came to Goal state , else - false.
     */
	@Override
	public boolean runSearchAlgo() {
        int limit = 0;
        BoardPuzzle root = this.currentBoard;
        while (true) {
            this.closedList.clear();
            this.openList.clear();
            boolean result = limitedDFS(root, limit);
            if (result) {
                this.cost = this.currentBoard.getDepth();
                return true;
            }
            limit++;
        }
	}
    /**
     * Check if we came to goal state with the current limit of depth. 
     * @param root is the current root of the board
     * @param limit is the current limit of depth.
     * @return true if we success came to goal state , else - false.
     */
    private boolean limitedDFS(BoardPuzzle root, int limit) {
        this.openList.push(root);
        while (!this.openList.isEmpty()) {
            this.currentBoard = this.openList.pop();
            this.closedList.add(this.currentBoard);
            if (this.currentBoard.isGoal()) {
                return true;
            }
            if (currentBoard.getDepth() == limit) {
                continue;
            }
            List<BoardPuzzle> successors = this.currentBoard.getSons();
            for (int i = successors.size() - 1; i > -1; i--) {
                this.openList.push(successors.get(i));
            }
        }
        return false;
    }

}
