package algorithm;

import java.util.*;

import static java.lang.String.format;

/**
 * @author 伏特加冰糖
 */
public class Id3Tree {
    DataBase forTrainingData;
    DataBase forTestingData;
    Integer deepth;

    public void setForTestingData(DataBase forTestingData) {
        this.forTestingData = forTestingData;
    }

    TreeNode root;

    public Id3Tree(DataBase forTrainingData) {
        this.forTrainingData = forTrainingData;
        this.deepth=10086;
        root = new TreeNode();
        if (forTrainingData.tupleArrayList.size() == 0) {
            System.out.println("非法字符");
            root = null;
            return;
        } else if (forTrainingData.tupleArrayList.size() == 1) {
            root.outPut = forTrainingData.tupleArrayList.get(0).labelValue;
            root.leafValue = root.outPut;
        }
        root.indexList = new ArrayList<>();
        root.outPut = "根节点";
        for (int i = 0; i < forTrainingData.tupleArrayList.size(); i++) {
            root.indexList.add(i);
        }
        createTree();
        this.deepth=-1;
    }

    public void createTree() {
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);
        while (!nodeQueue.isEmpty()) {
            TreeNode tempNode = nodeQueue.remove();
            Status tempStatus = forTrainingData.availableA(tempNode.indexList);
            if (tempStatus.oneStatus) {
                tempNode.leafValue = forTrainingData.tupleArrayList.get(tempNode.indexList.get(0)).labelValue;
                tempNode.attribute = "叶子节点";
                tempNode.indexList = null;
            } else {
                Attribute attribute = forTrainingData.highestInfoGainIndex(tempStatus.aList, tempNode.indexList);
                tempNode.attribute = attribute.name;
                tempNode.childList = new ArrayList<>();
                for (int i = 0; i < attribute.optionLists.size(); i++) {
                    TreeNode childNode = new TreeNode();
                    childNode.outPut = attribute.optionLists.get(i).optionName;
                    childNode.indexList = attribute.optionLists.get(i).indexList;
                    tempNode.childList.add(childNode);
                    nodeQueue.offer(childNode);
                }
            }
        }
    }

    /**
     * @apiNote 层序遍历决策树
     */
    public void printTree() {
        Queue<TreeNode> tempQueue = new LinkedList<>();
        TreeNode cur ;
        tempQueue.add(root);
        while (!tempQueue.isEmpty()) {
            cur = tempQueue.remove();
            String out;
            if(cur.leafValue==null){
                out=format("{(%s)%s}",cur.attribute,cur.outPut);
            }else{
                out=format("{(%s)%s}",cur.leafValue,cur.outPut);
            }
            System.out.println(out);
            if (cur.leafValue == null) {
                for (int i = 0, length = cur.childList.size(); i < length; i++) {
                    tempQueue.add(cur.childList.get(i));
                }
            }
        }
        System.out.println("遍历完成");
    }

    public TestResult test(Tuple target) throws SecondException{
        if(target.infoList.length!=this.forTrainingData.stringArrayList.size()){
            throw new SecondException("待测试数据集列数错误");
        }
        TestResult resultSet=new TestResult();
        resultSet.deepValues=1;
        TreeNode cur=this.root;
        while(cur.leafValue==null){
            boolean found=false;
            int flag=this.forTrainingData.attributeIndex(cur.attribute);
            resultSet.deepValues++;
            for (TreeNode tempNode: cur.childList) {
                if(target.infoList[flag].equals(tempNode.outPut)){
                    cur=tempNode;
                    found=true;
                    break;
                }
            }
            if(!found){
                resultSet.decisionRes = this.forTrainingData.whichMore(cur.indexList);
                resultSet.resourceRes = target.labelValue;
            }
        }
        resultSet.decisionRes = cur.leafValue;
        resultSet.resourceRes = target.labelValue;
        return resultSet;
    }

    public String testing(DataBase target)throws SecondException{
        StringBuffer sb=new StringBuffer("验证:");
        int deep = 0;
        boolean correct=true;
        ArrayList<ErrorResult> errorList = new ArrayList<>();
        for (int i = 0; i < target.tupleArrayList.size(); i++) {
            TestResult ts=this.test(target.tupleArrayList.get(i));
            if(ts.deepValues>deep){
                deep= ts.deepValues;
            }
            if(!Objects.equals(ts.decisionRes, ts.resourceRes)){
                correct=false;
                errorList.add(new ErrorResult(ts.decisionRes,i));
            }
        }
        sb.append(" 树的深度为:").append(deep);
        if(correct){
            sb.append(",且验证结果符合要求.\n");
            System.out.println(sb);
        }else{
            sb.append(",可是验证结果错误,出错的结果集如下:\n");
            System.out.println(sb);
            for (int i = 0; i < errorList.size(); i++) {
                for (int i1 = 0; i1 < target.tupleArrayList.get(errorList.get(i).index).infoList.length; i1++) {
                    sb.append(target.tupleArrayList.get(i).infoList[i1]+" ");
                }
                sb.append(format("原数据集上的标签值为%s,而决策的结果为%s\n",
                        target.tupleArrayList.get(errorList.get(i).index).labelValue,
                        errorList.get(i).errorInfo));
            }
        }
        return sb.toString();
    }

    public String verification(){
        StringBuilder stringBuffer=new StringBuilder("对于自身数据集的验证:\n");
        try {
            stringBuffer.append(this.testing(this.forTrainingData));
        } catch (SecondException e) {
            return e.toString();
        }
        return stringBuffer.toString();
    }

    public String examination(){
        StringBuilder stringBuffer=new StringBuilder("对于测试数据集的验证:\n");
        try {
            stringBuffer.append(this.testing(this.forTestingData));
        } catch (SecondException e) {
            return e.toString();
        }
        return stringBuffer.toString();
    }

    public ArrayList<TreeNode> testByOne(String[] strings)throws SecondException{
        if(strings.length!=this.forTrainingData.stringArrayList.size()+1){
            throw new SecondException("输入的元组属性数据有误");
        }
        ArrayList<TreeNode> result=new ArrayList<>();
        TreeNode cur=this.root;
        while (cur.leafValue==null){
            boolean found=false;
            result.add(cur);
            int flag = this.forTestingData.attributeIndex(cur.attribute);
            for(TreeNode tempNode: cur.childList){
                if(strings[flag].equals(tempNode.outPut)){
                    cur=tempNode;
                    found=true;
                    break;
                }
            }
            if(!found){
                TreeNode temp=new TreeNode();
                temp.leafValue=this.forTrainingData.whichMore(cur.indexList);
                temp.outPut="未定义";
                result.add(temp);
                return result;
            }
        }
        result.add(cur);
        return result;
    }
}
