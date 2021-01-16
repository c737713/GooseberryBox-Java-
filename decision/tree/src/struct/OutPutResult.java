package struct;


import algorithm.SecondException;

import java.util.ArrayList;
/**
 * @author Mr Chippy
 */
public class OutPutResult {
    Integer depth;
    StringBuffer[] outputSet;
    StringBuffer[] attributeSet;
    Integer[] numOfE;

    public OutPutResult(Integer depth) {
        this.depth = depth;
        outputSet=new StringBuffer[depth];
        attributeSet=new StringBuffer[depth];
        numOfE=new Integer[depth];
        for (int i = 0; i < depth; i++) {
            outputSet[i]=new StringBuffer();
            attributeSet[i]=new StringBuffer();
            numOfE[i]=0;
        }
    }

    public String getResult(ArrayList<OutPutNode> target) throws SecondException {
        if(target.size()==0){
            throw new SecondException("空树");
        }
        for (int i = 0; i < target.size(); i++) {
            OutPutNode cur=target.get(i);
            if(i==0){
                OutPutResult.addString(this.outputSet[cur.getDepth()], cur.outPut);
                OutPutResult.addString(this.attributeSet[cur.getDepth()],"("+cur.getAttribute()+")");
                numOfE[0]++;
            }else if(cur.theFirst) {
                int temp2 = -(numOfE[cur.depth] - numOfE[target.get(i - 1).getDepth()]) - 1;
                getSpace(outputSet[cur.depth], temp2);
                getSpace(attributeSet[cur.depth], temp2);
                OutPutResult.addString(this.outputSet[cur.getDepth()], cur.outPut);
                OutPutResult.addString(this.attributeSet[cur.getDepth()], "("+cur.getAttribute()+")");
                numOfE[cur.getDepth()] = numOfE[target.get(i - 1).depth];
            } else if(!target.get(i-1).theLast) {
                OutPutResult.addString(this.outputSet[cur.getDepth()], cur.outPut);
                OutPutResult.addString(this.attributeSet[cur.getDepth()], "("+cur.getAttribute()+")");
                numOfE[cur.getDepth()]=numOfE[target.get(i-1).depth]+1;
            }else{
                int temp=(numOfE[target.get(i-1).getDepth()]-numOfE[cur.getDepth()]);
                getSpace(this.outputSet[cur.getDepth()],temp);
                getSpace(this.attributeSet[cur.getDepth()],temp);
                OutPutResult.addString(this.outputSet[cur.getDepth()], cur.outPut);
                OutPutResult.addString(this.attributeSet[cur.getDepth()],"("+cur.getAttribute()+")");
                numOfE[cur.getDepth()]=numOfE[target.get(i-1).depth]+1;
            }
        }
        StringBuilder result=new StringBuilder("决策树打印开始:\n");
        for (int i = 0; i < this.depth; i++) {
            result.append(this.outputSet[i]).append("\n");
            result.append(this.attributeSet[i]).append("\n");
        }
        return result.toString();
    }

    public void getSpace(StringBuffer target,Integer num){
        target.append("\t".repeat(Math.max(0, num)));
    }

    public static void addString(StringBuffer target,String a){
       target.append(a).append("\t");
    }
}
