package algorithm;

import java.util.ArrayList;

/**
 * @author 伏特加冰糖
 */
public class TreeNode {
    String outPut;
    String leafValue;
    String attribute;

    ArrayList<Integer> indexList;
    ArrayList<TreeNode> childList;

    public TreeNode() {
        outPut = null;
        leafValue = null;
        indexList = new ArrayList<>();
        childList = null;
        attribute = null;
    }

    public String getOutPut() {
        return outPut;
    }

    public String getLeafValue() {
        return leafValue;
    }

    public String getAttribute() {
        return attribute;
    }
}
