package algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author 伏特加冰糖
 */
public class DataBase {
    //存储元组集
    ArrayList<Tuple> tupleArrayList;

    //存储属性名集
    ArrayList<String> stringArrayList;

    public DataBase(String[] strings) {
        tupleArrayList = new ArrayList<>();
        stringArrayList = new ArrayList<>();
        stringArrayList.addAll(Arrays.asList(strings));
    }

    public static DataBase getDataBaseByFile(String pn)throws Exception {
        ArrayList<String> strings = new ArrayList<>();
        FileReader fr = new FileReader(pn);
        BufferedReader bfReader = new BufferedReader(fr);
        String tempString = bfReader.readLine();
        while (tempString != null) {
            strings.add(tempString.trim());
            tempString = bfReader.readLine();
        }
        bfReader.close();
        fr.close();
        String[] tempStrings = strings.get(0).split(" ");
        DataBase db = new DataBase(tempStrings);
        db.stringArrayList.remove(db.stringArrayList.size() - 1);
        for (int i = 1, length = strings.size(); i < length; i++) {
            String[] ts=strings.get(i).split(" ");
            db.addTuple(ts);
        }
        return db;
    }

    /**@param optionList:添加元组
     * @apiNote 添加元组
     * */
    public void addTuple(String[] optionList) {
        Tuple temp;
        String[] strings=new String[this.stringArrayList.size()];
        try {
            String labelValue=optionList[optionList.length-1];
            System.arraycopy(optionList, 0, strings, 0, this.stringArrayList.size());
            temp = new Tuple(this, strings, labelValue);
            tupleArrayList.add(temp);
        } catch (SecondException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param tA:下标数组的范围
     * @return :信息熵
     * @apiNote :计算信息熵:区域内正标签比重与负标签比重经过一定计算得到的值,用来计算比重的优先级
     */

    private double informationEntropy(ArrayList<Integer> tA){
        ArrayList<Label> labelList=new ArrayList<>();
        for(Integer index:tA){
            String tempString=this.tupleArrayList.get(index).labelValue;
            boolean repeated=false;
            for (Label label : labelList) {
                if (tempString.equals(label.labelValue)) {
                    label.labelCount++;
                    repeated = true;
                    break;
                }
            }
            if(!repeated){
                labelList.add(new Label(tempString));
                labelList.get(labelList.size()-1).labelCount++;
            }
        }
        var result=0.0;
        for(Label temp:labelList){
            double ra= (double)labelList.size()/temp.labelCount;
            result-=ra*Math.log(ra)/Math.log(Math.E);
        }
        return result;
    }

    /**
     * @param iRange:返回下标的范围
     * @param attribute:被区分的属性
     * @return 被用属性分割的元组集
     * @apiNote 根据某一属性来分割元组集构成的数组, 届时会生成由(属性个数)个元组, 随后会通过计算当中信息熵最高的择优选取
     */
    private ArrayList<OptionList> differentiatedTupleSets(ArrayList<Integer> iRange, String attribute) {
        ArrayList<OptionList> tempList = new ArrayList<>();
        int attributeIndex = attributeIndex(attribute);
        for (Integer index : iRange) {
            //获取这个元组在这个属性的选项
            String temp = this.tupleArrayList.get(index).infoList[attributeIndex];
            int flag = notRepeated(tempList, temp);
            if (flag == -1) {
                //如果不重复则添加新的选项集
                tempList.add(new OptionList(temp));
                tempList.get(tempList.size() - 1).indexList.add(index);
            } else {
                //如果重复则为这个结果集内部的添加该下标
                tempList.get(flag).indexList.add(index);
            }
        }
        return tempList;
    }

    /**
     * @param target:待查重的ArrayList
     * @param checked:被查重的单词
     * @return 如果找到了则返回找到位置的下标, 否则返回-1
     */
    private Integer notRepeated(ArrayList<OptionList> target, String checked) {
        for (int i = 0; i < target.size(); i++) {
            if (target.get(i).optionName.equals(checked)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @apiNote 判断一个数据在一个数组里面是否存在, 存在的话返回下标, 不存在返回-1
     */
    private Integer notRepeated(ArrayList<Integer> target, Integer checked) {
        for (int i = 0; i < target.size(); i++) {
            if (target.get(i).equals(checked)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param target:被寻找的目标字符串的下标
     * @return 返回下标, 没找到则返回-1;
     */
    public Integer attributeIndex(String target) {
        for (int i = 0; i < this.stringArrayList.size(); i++) {
            if (Objects.equals(this.stringArrayList.get(i), target)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param s:属性名
     * @param aDouble:这个数节点已有的信息熵
     * @param iRange:元组的下标范围
     * @return 返回这个属性所对应的信息增益
     */
    private Attribute gainInformation(String s, Double aDouble, ArrayList<Integer> iRange) {
        ArrayList<OptionList> optionList = differentiatedTupleSets(iRange, s);
        var sum = 0.0;
        for (OptionList temp : optionList) {
            double tempNumber = informationEntropy(temp.indexList);
            sum += tempNumber;
        }
        return new Attribute(s, aDouble - sum, optionList);
    }

    /**
     * @param aRange:获取属性的范围
     * @param iRange:获取元组的范围
     * @return 信息增益最高的属性类
     */
    public Attribute highestInfoGainIndex(ArrayList<Integer> aRange, ArrayList<Integer> iRange) {
        Attribute[] resultSet = new Attribute[this.stringArrayList.size()];
        var maxIndex = aRange.get(0);
        var maxValue = -1.0;
        var aDouble = informationEntropy(iRange);
        for (Integer index : aRange) {
            resultSet[index] = gainInformation(stringArrayList.get(index), aDouble, iRange);
            if (resultSet[index].gainInformation > maxValue) {
                maxIndex = index;
                maxValue = resultSet[index].gainInformation;
            }
        }
        return resultSet[maxIndex];
    }

    /**
     * @param iRange:下标的范围
     * @apiNote 获取下标范围内第一个下标的属性值, 如果有不一样则将其录入, 反之则返回null, 同时记录
     */
    public Status availableA(ArrayList<Integer> iRange) {
        String[] tempContent = tupleArrayList.get(iRange.get(0)).infoList;
        String tempLabelValue = tupleArrayList.get(iRange.get(0)).labelValue;
        Status result = new Status();
        for (int i = 0, length = this.stringArrayList.size(); i < length; i++) {
            for (Integer integer : iRange) {
                if (notRepeated(result.aList, i) == -1 && !tempContent[i].equals(tupleArrayList.get(integer).infoList[i])) {
                    result.aList.add(i);
                }
                if (result.oneStatus && !tupleArrayList.get(integer).labelValue.equals(tempLabelValue)) {
                    result.oneStatus = false;
                }
            }
        }
        return result;
    }

    public String whichMore(ArrayList<Integer> iRange){
        ArrayList<Label> labelList=new ArrayList<>();
        for(Integer index:iRange){
            String tempString=this.tupleArrayList.get(index).labelValue;
            boolean repeated=false;
            for (Label label : labelList) {
                if (tempString.equals(label.labelValue)) {
                    label.labelCount++;
                    repeated = true;
                    break;
                }
            }
            if(!repeated){
                labelList.add(new Label(tempString));
                labelList.get(labelList.size()-1).labelCount++;
            }
        }
        int max=0;
        for (int i = 0; i < labelList.size(); i++) {
            if(labelList.get(i).labelCount>labelList.get(max).labelCount){
                max=i;
            }
        }
        return labelList.get(max).labelValue+"(缺乏对应模型)";
    }
}
