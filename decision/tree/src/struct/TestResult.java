package struct;

/**
 * @author Mr Chippy
 */
public class TestResult {
    String resourceRes;
    String decisionRes;
    Integer deepValues;

    public TestResult() {
    }

    public String getResourceRes() {
        return resourceRes;
    }

    public String getDecisionRes() {
        return decisionRes;
    }

    public Integer getDeepValues() {
        return deepValues;
    }

    public void setResourceRes(String resourceRes) {
        this.resourceRes = resourceRes;
    }

    public void setDecisionRes(String decisionRes) {
        this.decisionRes = decisionRes;
    }

    public void setDeepValues(Integer deepValues) {
        this.deepValues = deepValues;
    }
}
