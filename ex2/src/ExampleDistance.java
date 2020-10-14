/**
 * class of example distance object
 */
public class ExampleDistance {
    //the index of this distance in example of test
    private int index;
    //the distance
    private int distance;
    /**
     * constructor
     * @param index is index in arr of train data
     * @param distance is the distance hamming
     */
    public ExampleDistance(int index, int distance) {
        this.index = index;
        this.distance = distance;
    }

    /**
     * get the index of example distance in arr of example train
     * @return the index
     */
    public int getIndex() { return index;
    }

    /**
     * get the hamming distance of example distance from arr of example train
     * @return the distance
     */
    public int getDistance() {
        return distance;
    }
}
