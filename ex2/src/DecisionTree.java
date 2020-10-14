import java.util.HashMap;

/**
 * class that create decision tree with parameters: value , isLeaf , decision(like son), decisions(like sons)
 */
public class DecisionTree {
    //members
    private String value;
    //check if current node is leaf or not
    private boolean isLeaf;
    //the decision of node in current node
    private String decision;
    private HashMap<String, DecisionTree> decisions;
    /**
     * constructor of default decision tree
     * @param value is the value of node (if not leaf - null , else - value of classification type)
     * @param isLeaf
     * @param decision
     * @param decisions
     */
    public DecisionTree(String value, boolean isLeaf, String decision, HashMap<String,
            DecisionTree> decisions){
        this.value = value;
        this.isLeaf = isLeaf;
        this.decision = decision;
        this.decisions = new HashMap<>();
    }

    /**
     * function that get the value of current node
     * @return value of current node
     */
    public String getValue() {
        return value;
    }

    /**
     * function that get the decision of current node
     * @return decision of current node
     */
    public String getDecision() {
        return decision;
    }

    /**
     * function that get if current node is leaf or not
     * @return true if current node is leaf , else false
     */
    public boolean isLeaf() {
        return isLeaf;
    }

    /**
     * function that get the decisions of current node
     * @return the decisions of current node
     */
    public HashMap<String, DecisionTree> getDecisions() {
        return decisions;
    }

    /**
     * function that update the value of decision of current node
     * @param decision is the update decision of current node
     */
    public void setDecision(String decision) {
        this.decision = decision;
    }
    /**
     * function that update the value of decisions of current node
     * @param value is the update value of current node
     * @param node is the next node of current node
     */
    public void setDecisions(String value, DecisionTree node) {
        if(decisions.containsKey(value)){
            this.decisions.replace(value,node);
        } else {
            this.decisions.put(value,node);
        }

    }
    /**
     * function that update the value of current node
     * @param value is the update value of current node
     */
    public void setValue(String value) {
        this.value = value;
    }
    /**
     * function that update the value of leaf of current node
     * @param leaf is the update isLeaf of current node
     */
    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }
}
