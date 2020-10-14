//yakir pinchas 203200530
/**
	 * useful Enums for this exercise
	 */
public class UsefulEnums {
	//for the move of current state
    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    //for search algorithm
    enum SearchAlgorithms {
        IDS , BFS, A_STAR
    }
    //get the first letter from the direction ( r = right , l = left)
    public static String getOperatorAbbrevation(Direction dirPath) {
        String  enumName = dirPath.name();
        return enumName.substring(0,1);
    }
}