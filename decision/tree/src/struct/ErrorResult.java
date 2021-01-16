package struct;

/**
 * @author Mr Chippy
 */
public class ErrorResult {
    String errorInfo;
    Integer index;

    public ErrorResult(String errorInfo, Integer index) {
        this.errorInfo = errorInfo;
        this.index = index;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public Integer getIndex() {
        return index;
    }
}
