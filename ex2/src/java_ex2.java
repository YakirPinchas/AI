public class java_ex2 {

    public static void main(String[] args) {
        MathUtils mathUtils = new MathUtils();
        ActionFile actionFile = new ActionFile();
        String[][] dataTrain = actionFile.getData("train.txt");
        String[][] dataTest = actionFile.getData("test.txt");
        //copyTest in order to move about all lines of test file except first line
        String[][] copyTest = new String[dataTest.length - 1][];
        System.arraycopy(dataTest, 1, copyTest, 0, dataTest.length - 1);
        IDecisionAlgo[] algorithms = {new Id3(), new Knn(), new NaiveBase()};
        double[] accuracies = new double[algorithms.length];
        String[][] results = new String[algorithms.length][];
        int i = 0;
        for(IDecisionAlgo algorithm : algorithms) {
            algorithm.learnData(dataTrain);
            results[i] = algorithm.predictTest(copyTest);
            accuracies[i] = mathUtils.calcAccuracy(copyTest, results[i]);
            i++;
        }
        actionFile.writeResultsToFile(results,accuracies, algorithms.length);
    }
}
