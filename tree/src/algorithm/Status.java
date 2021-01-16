package algorithm;

import java.util.ArrayList;

/**
 * @author 伏特加冰糖
 */
public class Status {
    boolean oneStatus;
    ArrayList<Integer> aList;

    public Status(boolean oneStatus, ArrayList<Integer> arrayList) {
        this.oneStatus = oneStatus;
        this.aList = arrayList;
    }

    public Status() {
        oneStatus = true;
        aList = new ArrayList<>();
    }

}
