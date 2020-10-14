import java.util.*;
/**
 * class that implementation knn algorithm
 */
public class Knn implements IDecisionAlgo {
    //hash map of knn results in every iteration of line in test.
    private HashMap<String,Integer> knnResults;
    private String[][] copyData;
    private int k;
    /**
     * constructor of knn
     */
    public Knn() {
        k = 5; //define
        knnResults = new HashMap<>();
    }
    /**
     * function that learn the data from "train.txt" in order to knn algorithm
     * @param dataTrain is arr of data from "train.txt"
     */
    @Override
    public void learnData(String[][] dataTrain) {
        copyData = new String[dataTrain.length - 1][];
        //create copy array to dataTrain that he don't need first line of train.txt
        System.arraycopy(dataTrain, 1, copyData, 0, dataTrain.length - 1);
    }

    /**
     * function that add classification and counter of them knn results map
     * @param classification is the type classification
     */
    public void addToKnnResultsMap(String classification) {
        if(knnResults.containsKey(classification)){
            //if exist , increase the amount of this classification type in map
            knnResults.replace(classification, knnResults.get(classification) + 1);
        } else {
            //if not exist , create him and define his counter to 1
            knnResults.put(classification, 1);
        }
    }
    @Override
    /**
     * according knn algorithm , this function return the predict of specific test data
     * @param testData is the examples from the file "test data" that need to predict
     * @return the results of test data.
     */
    public String[] predictTest(String[][] testData) {
        //create member in order to manipulate math functions
        MathUtils mathUtils = new MathUtils();
        //results of the tests.
        String[] results = new String[testData.length];
        //create queue
        Queue<ExampleDistance> queue;
        for(int i = 0; i < testData.length; i++) {
            //define the knn results map every iteration of line in test
            knnResults = new HashMap<>();
            //define the queue to be priority queue with compearator of hamming distance
            queue = new PriorityQueue<>(new ExampleDistanceComperator());
            for(int m = 0; m < copyData.length - 1; m++) {
                int min = Math.min(copyData[m].length, testData[i].length - 1);
                //calculate that hamming distance of this example in train from the example in test
                int distanceHamming = 0;
                for(int j = 0; j < min; j++) {
                    distanceHamming += mathUtils.CalculateDistanceHamming(testData[i][j], copyData[m][j]);
                }
                //add the index of this example in train and his hamming distance to queue
                queue.add(new ExampleDistance(m,distanceHamming));
            }
            //take the k best results from queue
            for(int j = 0; j < k; j++){
                ExampleDistance trainLineChoose = queue.poll();
                //get the index choose in train
                int index = trainLineChoose.getIndex();
                //set the value of classification from this index in arr of data from train
                String classificationExample = copyData[index][copyData[index].length - 1];
                //add the value of classification to map
                addToKnnResultsMap(classificationExample);
            }
            //get the best value of classification in map
            results[i] = MaxClassification(knnResults);
        }
        return results;
    }
    /**
     * function that return the type classification with max values
     * @param results is hash map of type classification and amount of this values in map
     * @return best classification value
     */
    private String MaxClassification(HashMap<String,Integer> results) {
        //define int member to be a max of values in hash map
        int maxValueInMap=(Collections.max(results.values()));
        //iterative through hash map
        for (Map.Entry<String, Integer> entry : results.entrySet()) {
            if (entry.getValue()==maxValueInMap) {
                // Print the key with max value
                return entry.getKey();
            }
        }
        return null;
    }
}
