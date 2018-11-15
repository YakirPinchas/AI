import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS extends AbstractSearchAlgo {
	private Queue<BoardPuzzle> openList;
	public BFS(Integer[][] initBoard) {
		this.openList = new LinkedList<>();
		this.cost = 0;
		//init new board for this algo.
		this.currentBoard = new BoardPuzzle(initBoard,null,null);
		
	}
	@Override
	public boolean runSearchAlgo() {
		// TODO Auto-generated method stub
		openList.add(this.currentBoard);
        while (!this.openList.isEmpty()) {
            this.currentBoard = this.openList.remove();
            this.closedList.add(currentBoard);
            if (currentBoard.isGoal()) {
                return true;
            }
            List<BoardPuzzle> sons = this.currentBoard.getSons();
            for (BoardPuzzle son : sons) {
                this.openList.add(son);
            }
        }
        return false;
	}
	
}
