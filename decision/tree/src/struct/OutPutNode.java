package struct;

public class OutPutNode {
    String outPut;
    String leafValue;

    public String getLeafValue() {
        return leafValue;
    }

    public void setLeafValue(String leafValue) {
        this.leafValue = leafValue;
    }

    String attribute;
    Boolean theFirst;
    Boolean theLast;
    Integer depth;

    public OutPutNode(String outPut, String attribute, Boolean theFirst, Boolean theLast, Integer depth) {
        this.outPut = outPut;
        this.attribute = attribute;
        this.theFirst = theFirst;
        this.theLast = theLast;
        this.depth = depth;
    }

    public void setOutPut(String outPut) {
        this.outPut = outPut;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setTheFirst(Boolean theFirst) {
        this.theFirst = theFirst;
    }

    public void setTheLast(Boolean theLast) {
        this.theLast = theLast;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public String getOutPut() {
        return outPut;
    }

    public String getAttribute() {
        return attribute;
    }

    public Boolean getTheFirst() {
        return theFirst;
    }

    public Boolean getTheLast() {
        return theLast;
    }

    public Integer getDepth() {
        return depth;
    }
}
