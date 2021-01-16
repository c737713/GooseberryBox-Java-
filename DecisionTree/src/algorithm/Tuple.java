package algorithm;

/**
 * @author 伏特加冰糖
 */
public class Tuple {
    String[] infoList;
    String labelValue;

    public Tuple(DataBase target, String[] infoList, String labelValue) throws SecondException {
        if (infoList.length < target.stringArrayList.size()) {
            throw new SecondException("属性数量不足");
        }
        this.infoList = infoList;
        this.labelValue = labelValue;
    }
}
