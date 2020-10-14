public interface IDecisionAlgo {
    /**
     * function that learn the data and execute actions according specific algorithm
     * @param trainData is the examples from the file "train data"
     */
    void learnData(String[][] trainData);
    /**
     * according specific algorithm , this function return the predict of specific test data
     * @param testData is the examples from the file "test data" that need to predict
     * @return the results of test data.
     */
    String[] predictTest(String[][] testData);
}
