import java.util.*;

/**
 * class that implementation ID3 algorithm
 */
public class Id3 implements IDecisionAlgo {
    //arr of names of attributes in train data.
    private String[] attributesName;
    //the name of classification class
    private String classificationTarget;
    //the current node in tree.
    private DecisionTree node;
    /**
     * function that learn the data from "train.txt" in order to ID3 algorithm
     * @param trainData is arr of data from "train.txt"
     */
    @Override
    public void learnData(String[][] trainData) {
        ActionFile actionFile = new ActionFile();
        attributesName = trainData[0];
        //copy data didn't need the first line of "train.txt"
        String[][] copyData = new String[trainData.length - 1][];
        //only information about class move to copyData.
        System.arraycopy(trainData, 1, copyData, 0, trainData.length - 1);
        //the name of classification target
        classificationTarget = attributesName[attributesName.length - 1];
        //function that implementation the pesudo code of DTL.
        node = buildTree(copyData, attributesName, classificationTarget);
        //function that create the string that i need in output_tree.txt
        String treeStr = getTreeString(node, 0);
        //function that write the tree to output_test.txt according to requirements execersies
        actionFile.write("output_tree.txt",treeStr);
    }
    /**
     * function that create the tree in txt file according to function "build tree"
     * @param current is the current node in tree
     * @param depth is the depth of current node in tree.
     * @return the string that contains the all tree.
     */
    private String getTreeString(DecisionTree current, int depth) {
        StringBuilder tree = new StringBuilder();
        Map<String, DecisionTree> decisions = current.getDecisions();
        if(decisions.size() == 0) {
            tree.append(":");
            //in the last this is the value.
            tree.append(current.getValue());
        }
        ArrayList<String> decisionsArr = new ArrayList<>(decisions.keySet());
        //check if in decisions of current node exist 1 type.
        tree.append("\n");
        //if depth = 0 , return ""
        if(depth == 0) {
            tree.replace(0, tree.length(), "");
        }
        // sort the nodes according to alpbetical order
        Collections.sort(decisionsArr);
        // go over each successor
        for(String key : decisionsArr) {
            if(depth > 0) {
                for(int i = 0; i < depth; i++) {
                    tree.append("\t");
                }
                tree.append("|");
            }
            tree.append(current.getDecision()).append("=").append(key);
            tree.append(getTreeString(decisions.get(key), depth + 1));
        }
        return tree.toString();
    }

    /**
     * function that build the tree about pseudo code of DTL
     * @param trainData is the array of data from "train.txt"
     * @param attributesName is arr of names of attributes in "train.txt"
     * @param classificationTarget is the name of classification target in "train.txt"
     * @return the tree that create according to train.
     */
    public DecisionTree buildTree(String[][] trainData, String[] attributesName, String classificationTarget){

        DecisionTree newNode,subNode;
        //list of classification
        List<String> classification = new LinkedList<>();
        //check the value of classification in example train and add them to list
        for(String[] line : trainData) {
            String classificationExample = line[line.length - 1];
            classification.add(classificationExample);
        }
        //check what is the mode of this example , NEED TO WORK!
        String decisionClassification = getModeClassification(trainData,attributesName,
                classificationTarget);
        //check how much classification type with this value exist in list.
        int amountOfValue = getAmountOfValueInList(classification, classification.get(0));
        //check if data is empty or in attributes remained only the target attribute
        if((trainData.length == 0) || (attributesName.length == 1)) {
            //the value of leaf found in decision classification.
            return new DecisionTree(decisionClassification,true,null,null);
        } else if(classification.size() == amountOfValue) {
          //all values are the same so return this value and we came to leaf in tree
            //the value of leaf found in first place of list.
            return new DecisionTree(classification.get(0),true,null,null);
        } else {
            //return the best attribute of current node.
            String bestAttribute = chooseBestAttribute(trainData, attributesName, classificationTarget);
            //create new node in tree according to best attribute.
            newNode = new DecisionTree(null,false,bestAttribute,null);

            HashSet<String> bestAttributeValues = getValues(trainData,attributesName,bestAttribute);
            //loop for create tree and move about net data
            for(String val: bestAttributeValues) {
                //create new data
                String[][] newData = screenData(trainData, attributesName,bestAttribute,val);
                //remove the attribute that we already exist.
                String[] newAttributes = createNewAttributes(attributesName,bestAttribute);
                //build tree with new data about sub node.
                subNode = buildTree(newData, newAttributes, classificationTarget);
                //System.out.println(subNode.getValue() + "," + subNode.getDecision());
                //add to decisions map the value of this current node and his sub node
                newNode.setDecisions(val,subNode);
            }
        }
        return newNode;
    }
    /**
     * function that create new arr of attribute that we aren't already use them
     * @param attributesName is the current arr of attribute
     * @param bestAtt is the best attribute of this node
     * @return the new arr of attributes
     */
    private String[] createNewAttributes(String[] attributesName, String bestAtt) {
        String[] newAttributes = new String[attributesName.length - 1];
        int i = 0, j = 0;
        for(i = 0; i < attributesName.length;i++)
        {
            //if i already use in this att , i don't need him in new arr
            if(attributesName[i] != bestAtt){
                newAttributes[j] = attributesName[i];
                j++;
            }
        }
        return newAttributes;
    }
    /**
     * function that screen the relevanet data
     * @param data is arr of data.
     * @param attributes is arr of names of attributes
     * @param att is the best attribute that i already used
     * @param value is the value of decision
     * @return the new data
     */
    private String[][] screenData(String[][] data,String[] attributes, String att, String value) {
        String[][] screenedData;
        int i = 0;
        if(att == null || att.equals("=")) {
            return data;
        }
        for(String attribute : attributes) {
            if(attribute.equals(att)) {
                break;
            }
            i++;
        }
        ArrayList<String[]> screenedRows = new ArrayList<>();
        for(String[] row : data) {
            if(value.endsWith(att)) {
                value = value.substring(0, value.length() - att.length());
            }
            if (row[i].equals(value.replace(att, ""))) {
                ArrayList<String> newRow = new ArrayList<>();
                for(int j = 0; j < row.length; j++) {
                    if(i != j) {
                        newRow.add(row[j]);
                    }
                }
                String[] newAdjustedRow = new String[newRow.size()];
                for(int j = 0; j < newAdjustedRow.length; j++) {
                    newAdjustedRow[j] = newRow.get(j);
                }
                screenedRows.add(newAdjustedRow);
            }
        }
        screenedData = new String[screenedRows.size()][];
        for(int j = 0; j < screenedRows.size(); j++) {
            screenedData[j] = screenedRows.get(j);
        }
        return screenedData;
    }
    /**
     * function that get list of values of  data train in example like "attributes[best att]"
     * @param dataTrain is the data of train
     * @param attributesName is arr of name of attribute
     * @param bestAtt is the best attribute
     * @return hash set of values
     */
    public HashSet<String> getValues(String[][] dataTrain, String[] attributesName, String bestAtt) {

        HashSet<String> values = new HashSet<>();
        //get the index of best attribute in attribute name
        int index = getIndex(attributesName,bestAtt);
        for(String[] entry: dataTrain){
            //add the value in example train to values
            values.add(entry[index]);
        }
        return values;
    }

    /**
     * function that choose the best attribute with best gain in this node.
     * @param trainData is arr of data from train
     * @param attributesName is arr of names of attributes
     * @param classificationTarget is the name of classification target
     * @return best attribute
     */
    public String chooseBestAttribute(String[][] trainData, String[] attributesName, String classificationTarget){
        //define current maxGain
        double maxGain = 0;
        //define best attribute
        String bestAtt = attributesName[0];
        //loop for to choose the best attribute in arr of attributes according to their gain value
        for(String att:attributesName){
            if(att != classificationTarget) {
                //calc the gain of this name of attribute
                double currentGain = calcGain(trainData,attributesName,att,classificationTarget);
                if (currentGain > maxGain) {
                    //update the maxGain and Best attributes
                    maxGain = currentGain;
                    bestAtt = att;
                }
            }
        }
        return bestAtt;
    }

    /**
     * function that calculate the gain of this attribute
     * @param trainData is arr of data from train
     * @param attributesName is arr of names of attributes
     * @param att is the best attribute
     * @param classificationTarget is a classification target
     * @return gain of this attribute
     */
    public double calcGain(String[][] trainData, String[] attributesName,String att, String classificationTarget) {
        HashMap<String,Integer> frequentAtt = new HashMap<>();
        //get index of target in attributes.
        int index = getIndex(attributesName, att);
        //check how much values exists to any type in classification
        for(String[] line : trainData) {
            if(frequentAtt.containsKey(line[index])) {
                int prevCount = frequentAtt.get(line[index]);
                //update how much value contains line[index]
                frequentAtt.replace(line[index], prevCount + 1);
            } else {
                frequentAtt.put(line[index], 1);
            }
        }
        //define entropy member
        double currentEntropy = 0;

        for(String key : frequentAtt.keySet()) {
            //check how much key exist from all hash map.
            double probability = frequentAtt.get(key) / sumAmountOfValueInHashMap(frequentAtt);
            //subset data
            String[][] dataSubSet = subSetData(trainData, index, key);
            //calculate the current entropy
            currentEntropy += probability * calcEntropy(dataSubSet, attributesName, classificationTarget);
        }
        return calcEntropy(trainData, attributesName, classificationTarget) - currentEntropy;
    }

    /**
     * function that calculate the entropy of example
     * @param trainData is arr of data from the train
     * @param attributesName is arr of names of attributes
     * @param classificationTarget is a classification target
     * @return the entropy of this example of attribute
     */
    public double calcEntropy(String[][] trainData, String[] attributesName, String classificationTarget) {
        HashMap<String,Integer> frequentAtt = new HashMap<>();
        //get index of target in attributes.
        int index = getIndex(attributesName, classificationTarget);
        for(String[] line : trainData) {
            if(frequentAtt.containsKey(line[index])) {
                int prevCount = frequentAtt.get(line[index]);
                frequentAtt.replace(line[index], prevCount + 1);
            } else {
                frequentAtt.put(line[index], 1);
            }
        }
        //define the entropy member
        double currentEntropy = 0;
        for(int freq :frequentAtt.values()){
            //frequent = sum of this example / sum of all examples in this node
            double frequent = (double) freq / trainData.length;
            //calculate -frequent * log2(frequent)
            currentEntropy += -frequent * xlogx(frequent,freq);
        }
        return currentEntropy;
    }

    /**
     * function that calculate the part of xlogx in entropy
     * @param per is the number that need to be x
     * @param amount is the sum of this examples in train
     * @return xlogx.
     */
    public double xlogx(double per, int amount) {
        double logClassification = 0;
        //if amount = 0 return 0;
        if(amount!= 0) {
            logClassification = Math.log(per) / Math.log(2);
        } else {
            logClassification = 0;
        }
        return logClassification;
    }

    /**
     * function that update the data according this node
     * @param trainData is arr of data from train
     * @param index is specific index
     * @param key is the key that need in order to maniplate sub set data
     * @return the new data
     */
    public String[][] subSetData(String[][] trainData, int index, String key) {
        ArrayList<ArrayList<String>> lines = new ArrayList<>();
        String[][] results;
        //split the data in list.
        for(String[] entry: trainData) {
            if (entry[index].compareTo(key) == 0){
                List<String> arr = Arrays.asList(entry);
                ArrayList<String> values = new ArrayList<>(arr);
                lines.add(values);
            }
        }
        //move from list to arr
        results = new String[lines.size()][lines.get(0).size()];
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).size(); j++) {
                results[i][j] = lines.get(i).get(j);
            }
        }
        return results;
    }

    /**
     * function that check how much sum of values exist in this hash map
     * @param frequentAtt is hash map of string and integer
     * @return the sum of  values in this hash map
     */
    public double sumAmountOfValueInHashMap(HashMap<String,Integer> frequentAtt) {
        double amountOfValues = 0;
        for (String key: frequentAtt.keySet()) {
            amountOfValues += frequentAtt.get(key);
        }
        return amountOfValues;
    }

    /**
     * function that return the sum of this value in list
     * @param classification is the list
     * @param value is the value
     * @return counter of this value in list
     */
    public int getAmountOfValueInList(List<String> classification, String value){
        int counterValue = 0;
        for (int i = 0;i < classification.size();i++) {
            if(value.compareTo(classification.get(i)) == 0){
                counterValue++;
            }
        }
        return counterValue;
    }

    /**
     * function that return the major classification in this node
     * @param trainData is arr of data from train
     * @param attributesName is arr of names of attributes
     * @param classificationTarget is classification target
     * @return classification value.
     */
    public String getModeClassification(String[][] trainData,String[] attributesName,
                                        String classificationTarget){
        HashMap<String,Integer> amountAtt = new HashMap<>();
        //get index of target in attributes.
        int index = getIndex(attributesName,classificationTarget);
        for(String[] line : trainData) {
            if(amountAtt.containsKey(line[index])) {
                int prevCount = amountAtt.get(line[index]);
                amountAtt.replace(line[index], prevCount + 1);
                //System.out.println(line[index] + "," + (prevCount+1));
            } else {
                amountAtt.put(line[index], 1);
            }
        }
        //Checking which decision has a higher value
        int maxValueInMap=(Collections.max(amountAtt.values()));
        //iterative through hash map
        for (Map.Entry<String, Integer> entry : amountAtt.entrySet()) {
            if (entry.getValue()==maxValueInMap) {
                // Print the key with max value
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * function that check who is the index of classification target in arr of attribute
     * @param attributesName is arr of attribute
     * @param classificationTarget is the attribute
     * @return index of this attribute
     */
    public int getIndex(String[] attributesName, String classificationTarget) {
        for(int i = 0; i < attributesName.length; i++) {
            if(attributesName[i] == classificationTarget) {
                return i;
            }
        }
        return 0;
    }

    /**
     * function that predict the results of test data according to the tree that i create in build tree
     * @param testData is the examples from the file "test data" that need to predict
     * @return the results
     */
    @Override
    public String[] predictTest(String[][] testData) {
        String[] results = new String[testData.length];
        int i = 0;
        //for all line of data in test check the predication
        for(String[] exampleTest:testData){
            results[i] = prediction(exampleTest, attributesName, node);
            i++;
        }
        return results;
    }

    /**
     * check the classification value of this line in test
     * @param exampleTest is the current line of test
     * @param attributesName is arr of names of attribute
     * @param node is the current node
     * @return classification value
     */
    public String prediction(String[] exampleTest, String[] attributesName, DecisionTree node) {
        if(node.isLeaf() == true){
            return node.getValue();
        }
        //get value of node
        String decisionValue = node.getDecision();
        //System.out.println(decisionValue);
        int index = getIndex(attributesName, decisionValue);
        //get the value of best att
        String value = exampleTest[index];
        //calling the function recursively with the decisions branch according to the value
        return prediction(exampleTest, attributesName, node.getDecisions().get(value));
    }
}
