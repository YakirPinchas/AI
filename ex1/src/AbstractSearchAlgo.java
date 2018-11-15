import java.util.ArrayList;
import java.util.List;

//yakir pinchas 203200530
abstract public class AbstractSearchAlgo implements ISearchAlgo {
	
	protected int cost;
	protected List<BoardPuzzle> closedList = new ArrayList<>();
	protected BoardPuzzle currentBoard;
	
	
	@Override
	abstract public boolean runSearchAlgo();

	@Override
	public int getClosedListSize() {
		// TODO Auto-generated method stub
		return closedList.size();
	}

	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return cost;
	}

	@Override
	public String getSolutionPath() {
		// TODO Auto-generated method stub
		StringBuilder stringBuilder = new StringBuilder();
		BoardPuzzle current = currentBoard;
		
		while(current.getParent()!= null) {
			stringBuilder.insert(0,UsefulEnums.getOperatorAbbrevation(current.getOriginPath()));
			current = current.getParent();
		}
		
		return stringBuilder.toString();
	}

}
