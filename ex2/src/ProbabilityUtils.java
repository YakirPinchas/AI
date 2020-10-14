public class ProbabilityUtils {
    /**
     * initial arr of probability value to 1
     * @param size is the size of arr
     * @return initial array of probability
     */
    public double[] InitialProbability(int size) {
        int i = 0;
        double[] probability = new double[size];
        for(i = 0; i < probability.length; i++) {
            probability[i] = 1;
        }
        return probability;
    }

    /**
     * calculate the P(att1 | classification) * P (att2 | classification) * .... with smooth
     * @param probability is arr of probability
     * @param counts is arr of counts
     * @param classificationCounts is arr of count of classification
     * @return update arr probability
     */
    public double[] CalculateConditionalProbability(double[] probability, int[] counts
            ,int[] classificationCounts, int[] sumCorrectAtt){
        int i = 0;
        int denominator = 0 ;
        int counter = 0;
        for(i = 0; i < probability.length; i++) {
            counter = counts[i] + 1;
            denominator = classificationCounts[i] + sumCorrectAtt[i];
            probability[i] *= (double)counter / denominator;
        }
        return probability;
    }

    /**
     * calculate probability of current classification
     * @param probability is arr of probability
     * @param classificationCounts is arr of counts of classification in train data
     * @param classificationSum is the amount of all classification in train data
     * @return
     */
    public double[] CalculateProbabilityClassification(double[] probability, int[] classificationCounts, int classificationSum) {
        int i = 0;
        for(i = 0; i < probability.length; i++) {
            probability[i] *= (double)classificationCounts[i] / classificationSum;
        }
        return probability;
    }

}