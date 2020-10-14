import java.util.ArrayList;
import java.util.List;
/**
 * Interface of search algorithm
 */
abstract public class AbstractSearchAlgo implements ISearchAlgo {
	//Members
	protected int cost;
	protected List<BoardPuzzle> closedList = new ArrayList<>();
	protected BoardPuzzle currentBoard;
	/**
	 * run the specific search algorithm
	 * @return true - if goal state was found, false - else 
	 */
	@Override
	abstract public boolean runSearchAlgo();
	/**
	 * gets the size of the closed List in the end of the algorithm
	 * @return the size of closed list 
	 */
	@Override
	public int getClosedListSize() {
		// TODO Auto-generated method stub
		return closedList.size();
	}
	/**
	 * gets the specific cost of the search
	 * @return the size of closed list 
	 */	
	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return cost;
	}
    /**
     * get the path to the goal from the initial state.
     * @return solution path.
     */
	@Override
	public String getSolutionPath() {
		// TODO Auto-generated method stub
		StringBuilder stringBuilder = new StringBuilder();
		BoardPuzzle current = currentBoard;
		
		while(current.getParent()!= null) {
			//insert to stringBuilder the sign of the move between parent to current board
			stringBuilder.insert(0,UsefulEnums.getOperatorAbbrevation(current.getOriginPath()));
			//working with previous board
			current = current.getParent();
		}
		//return the sentence with all moves in path (from initial board to goal board).
		return stringBuilder.toString();
	}

}
