package algorithm;

import java.util.ArrayList;

/**
 * @author 伏特加冰糖
 */
public class OptionList {
    String optionName;
    ArrayList<Integer> indexList;

    public OptionList(String optionName) {
        this.optionName = optionName;
        indexList = new ArrayList<>();
    }
}
