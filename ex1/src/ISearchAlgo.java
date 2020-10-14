
public interface ISearchAlgo {
    /**
     * function that run the search algorithm
     * @return true - if this is goal state , else - false
     */
	boolean runSearchAlgo();
	/**
	 * function that calculate the size of closed list
	 * @return the size of closed list
	 */
    int getClosedListSize();
    /**
     * function that get the cost of algorithm
     * @return cost of algorithm
     */
    int getCost();
    /**
     * function that return the path of board until to goal state
     * @return the path
     */
    String getSolutionPath();
}
