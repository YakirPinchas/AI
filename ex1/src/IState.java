/**
 * Interface of state of current Board
 */
public interface IState<B, D> {
	/**
	 * get the current Board
	 * @return B - current Board
	 */
    B getBoard();
    /**
	 * get the parent of this node
	 * @return IState - the parent of the node
	 */
    IState getParent();
    /**
	 * get the current Board
	 * @return D - The path from beginning of the path until end.
	 */
    D getOriginPath();
}