//yakir pinchas 203200530
public class UsefulEnums {
    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    enum SearchAlgorithms {
        IDS , BFS, A_STAR
    }
    //get the first letter from the direction ( r = right , l = left)
    public static String getOperatorAbbrevation(Direction dirPath) {
        String  enumName = dirPath.name();
        return enumName.substring(0,1);
    }
}