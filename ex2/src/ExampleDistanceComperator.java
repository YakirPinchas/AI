import java.util.Comparator;

/**
 * class of comperator of hamming distance
 */
public class ExampleDistanceComperator implements Comparator<ExampleDistance> {
    /**
     * comperator of example distance according to hamming distance
     * @param o1 is first example distance
     * @param o2 is second example distance
     * @return 1 - if o1 win , -1 - if o2 win.
     */
    @Override
    public int compare(ExampleDistance o1, ExampleDistance o2) {
        //if hamming distance of two examples equal , return the little index
        if(o1.getDistance() == o2.getDistance()) {
            return Integer.compare(o1.getIndex(), o2.getIndex());
        }
        return Integer.compare(o1.getDistance(), o2.getDistance());
    }
}