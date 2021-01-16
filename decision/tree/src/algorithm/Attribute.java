package algorithm;

import java.util.ArrayList;

/**
 * @author 伏特加冰糖
 */
public class Attribute {
    String name;
    Double gainInformation;
    ArrayList<OptionList> optionLists ;


    public Attribute(String name, ArrayList<OptionList> optionLists) {
        this.name = name;
        this.optionLists = optionLists;
        this.gainInformation = 0.0;
    }

    public Attribute(String name, Double gainInformation, ArrayList<OptionList> optionLists) {
        this.name = name;
        this.gainInformation = gainInformation;
        this.optionLists = optionLists;
    }
}
