import java.util.List;
import java.util.Stack;

public class IDS extends AbstractSearchAlgo{
    
	private Stack<BoardPuzzle> openList;

    public IDS(Integer[][] initBoard) {
        this.currentBoard = new BoardPuzzle(initBoard, null, null);
        this.openList = new Stack<>();
        this.cost = 0;
    }

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
