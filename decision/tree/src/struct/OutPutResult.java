package struct;

import java.util.ArrayList;

/**
 * @author Mr Chippy
 */
public class OutPutResult {
    Integer depth;
    ArrayList<StringBuffer> outputSet;
    ArrayList<StringBuffer> attributeSet;
    String res;

    public OutPutResult(Integer depth) {
        this.depth = depth;
        outputSet=new ArrayList<>();
        attributeSet=new ArrayList<>();
        res=null;
    }
}
