public class MathUtils {
    /**
     * function that calculate the sum of all the items in arr
     * @param arr is the arr of numbers
     * @return the sum of arr
     */
    public int Sum(int[] arr) {
        int sum = 0;
        for(int num : arr) {
            sum += num;
        }
        return sum;
    }
    /**
     * function that check if the item of att in line of test similar to item of att in line of train
     * @param trainItem is the item of att in line of train
     * @param testItem is the item of att in line of test
     * @return 0 if similar and 1 if different
     */
    public int CalculateDistanceHamming(String trainItem, String testItem) {
        if(trainItem.equals(testItem)) {
            return 0;
        }
        return 1;
    }
    /**
     * function that check who is the max classification between all the classifications
     * for knn algorithm
     * @param classifications is arr of classification
     * @param probability is arr of probability of classification
     * @return the max classification
     */
    public String getMaxClassificationNB(String[] classifications, double[] probability) {
        int maxIndex = -1;
        double maxVal = 0;
        for(int i = 0; i < classifications.length; i++) {
            if(probability[i] > maxVal) {
                maxIndex = i;
                maxVal = probability[i];
            }
        }
        return classifications[maxIndex];
    }
    /**
     * function that calculate the accuracy of my predict to test predict
     * @param testData is the data from "test.txt"
     * @param results is arr of my results
     * @return accuracy of similar
     */
    public static double calcAccuracy(String[][] testData, String[] results) {
        int accuracies = 0;
        //check if my result similar to test result.
        for(int i = 0; i < testData.length; i++) {
            if(testData[i][testData[i].length - 1].compareTo(results[i]) == 0) {
                accuracies++;
            }
        }
        return (double)accuracies / testData.length;
    }
}
