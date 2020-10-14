import java.util.*;

/**
 * class that implementation Naive Base algorithm
 */
public class NaiveBase implements IDecisionAlgo {
    private Map<String, Integer> count;
    private Map<String, Set<String>> amountTypeDiffAtt;
    private int[] sumAmountTypeDiffAtt;
    private Set<String> classification;
    private String[] attributes;
    /**
     * constructor of naive base
     */
    public NaiveBase() {
        count = new HashMap<>();
        amountTypeDiffAtt = new HashMap<>();
        classification = new HashSet<>();
    }

    /**
     * function that learn the data and execute actions according naive base algorithm
     * @param dataTrain is the examples from the file "train data"
     */
    @Override
    public void learnData(String[][] dataTrain) {
        String[][] copyData = new String[dataTrain.length - 1][];
        attributes = dataTrain[0];
        initialTypeAttMap(attributes);
        sumAmountTypeDiffAtt = new int[attributes.length];
        int j = 0;
        //initial all of values in arr to 0.
        for (j = 0;j < sumAmountTypeDiffAtt.length;j++){
            sumAmountTypeDiffAtt[j] = 0;
        }
        //copy the data to copy data
        System.arraycopy(dataTrain, 1, copyData, 0, dataTrain.length - 1);
        for(String[] line : copyData) {
            //the option of classification col
            classification.add(line[line.length - 1]);
            for(int i = 0; i < line.length - 1; i++) {
                //line of current example of attribute with correct classification and correct attributes
                String exampleTrain = line[i] + "," + line[line.length - 1] + "," + attributes[i];
                addToTypeAttMap(attributes[i],line[i],i);
                addToCountMap(exampleTrain);
            }
            addToCountMap(line[line.length - 1]);
        }
    }

    /**
     * add the item to count map
     * @param item is the item
     */
    private void addToCountMap(String item) {
        if(count.containsKey(item)) {
            count.replace(item, count.get(item) + 1);
        } else {
            count.put(item, 1);
        }
    }

    private void initialTypeAttMap(String[] attributes) {
        for(int j = 0; j < attributes.length -1; j++) {
            amountTypeDiffAtt.put(attributes[j],new HashSet<>());
        }
    }
    /**
     * add attribute and att type to type att map
     * @param att is attribute
     * @param attType is type of attribute
     * @param i is the index
     */
    private void addToTypeAttMap(String att,String attType, int i) {
        amountTypeDiffAtt.get(att).add(attType);
        sumAmountTypeDiffAtt[i] = amountTypeDiffAtt.get(att).size();
    }
    /**
     * according naive base algorithm , this function return the predict of specific test data
     * @param testData is the examples from the file "test data" that need to predict
     * @return the results of test data.
     */
    @Override
    public String[] predictTest(String[][] testData) {

        String[] results = new String[testData.length];
        String[] classificationArr = new String[classification.size()];
        //array of counts of example in classification
        int counts[] = new int[classification.size()];
        int classificationCounts[] = new int[classification.size()];
        //classification sum is how much rows exists in train data
        int i = 0, classificationSum = 0, indexResults = 0;
        //probability of all stages of naive base
        double[] probability;
        ProbabilityUtils probabilityUtils = new ProbabilityUtils();
        MathUtils mathUtils = new MathUtils();

        for(String classificationStr : classification) {
            //check how much attstr appear
            if(count.get(classificationStr) == null)
                classificationCounts[i] = 0;
            else
                classificationCounts[i] = count.get(classificationStr);
            //check how much different attstr appear until now
            classificationSum += classificationCounts[i];
            //the name of classification (for example : yes/no)
            classificationArr[i] = classificationStr;
            i++;
        }

        for(String[] testLine : testData) {
            String[] copyLine = new String[testLine.length - 1];
            // copy lines fields
            System.arraycopy(testLine, 0, copyLine, 0, testLine.length - 1);
            // initialize probability array
            probability = probabilityUtils.InitialProbability(classification.size());
            //for current attribute
            int attIndex = 0;
            for(String item : copyLine) {
                //sum of this example test in train
                int sum = 0;
                for(i = 0; i < counts.length; i++) {
                    String exampleTest = item + "," + classificationArr[i] + "," + attributes[attIndex];
                    //check how much examples in train similar to current line of test
                    if(count.get(exampleTest) == null)
                        counts[i] = 0;
                    else
                        counts[i] = count.get(exampleTest);
                    sum += counts[i];
                }
                //calculate the P(att1 | classification) * P (att2 | classification) * .... with smooth
                probability = probabilityUtils.CalculateConditionalProbability
                        (probability,counts,classificationCounts,sumAmountTypeDiffAtt);
                attIndex++;
            }
            //calculate P(classification) and add to sum.
            probability = probabilityUtils.CalculateProbabilityClassification
                    (probability,classificationCounts,classificationSum);
            //check which class with higher probability
            results[indexResults] = mathUtils.getMaxClassificationNB(classificationArr, probability);

            indexResults++;
        }
        return results;
    }
}
