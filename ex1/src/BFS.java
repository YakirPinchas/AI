import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
/*
 * the BFS algorithm
 */
public class BFS extends AbstractSearchAlgo {
	//in BFS we use in Queue
	private Queue<BoardPuzzle> openList;
    /**
     * Constructor of BFS
     * @param init board is the initial board that we get from the input file
     */
	public BFS(Integer[][] initBoard) {
		this.openList = new LinkedList<>();
		//the cost in BFS = 0
		this.cost = 0;
		//initial new board for this algorithm.
		this.currentBoard = new BoardPuzzle(initBoard,null,null);
		
	}
    /**
     * run the algorithm of BFS
     * @return true if we came to Goal state , else - false.
     */
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
