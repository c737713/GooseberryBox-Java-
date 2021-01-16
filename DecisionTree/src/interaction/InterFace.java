package interaction;

import algorithm.DataBase;
import algorithm.Id3Tree;
import algorithm.SecondException;
import algorithm.TreeNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Mr Chippy
 */
public class InterFace {
    public static void main(String[] args) {
        final Id3Tree[] id3Tree = {null};
        final int columnWidth=38;

        JFrame jf = new JFrame("决策树(id3)");
        jf.setBounds(400,300,410,500);
        jf.setLayout(new FlowLayout(FlowLayout.LEFT));
        jf.setResizable(false);

        JLabel text1=new JLabel("输入训练集路径:");
        JTextField jTextField1 = new JTextField("D:\\gitArea\\GooseberryBox-Java-\\DecisionTree\\src\\datasource\\d.txt",columnWidth);
        JLabel text3=new JLabel("输入测试集路径:");
        JTextField jTextField2 = new JTextField("D:\\gitArea\\GooseberryBox-Java-\\DecisionTree\\src\\datasource\\e.txt",columnWidth);
        JLabel text4 =new JLabel("单条内容测试:");
        JTextField jTextField3 = new JTextField("晴 中 低 是 true",columnWidth);
        JButton pullIn = new JButton("引入");
        JButton ver = new JButton("验证");
        JButton exam = new JButton("测试");
        JButton testOneByOne = new JButton("逐个测试");
        JButton print = new JButton("层序打印");
        JLabel text5 = new JLabel("显示区:                                               ");
        JTextArea textArea =new JTextArea("",15,columnWidth);
        textArea.setLineWrap(true);
        JPanel jPanel=new JPanel();
        jPanel.add(new JScrollPane(textArea));

        pullIn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    id3Tree[0]=null;
                    Id3Tree tempId3Tree=new Id3Tree(DataBase.getDataBaseByFile(jTextField1.getText()));
                    tempId3Tree.setForTestingData(DataBase.getDataBaseByFile(jTextField2.getText()));
                    id3Tree[0] =tempId3Tree;
                    textArea.append("决策树构建完成\n");
                }catch (FileNotFoundException e){
                    textArea.append("文件未被找到,请检查路径\n");
                }catch (IOException e){
                    textArea.append("文件输入流发生错误,请检查路径\n");
                }catch (Exception e){
                    textArea.append("发生错误,请检查\n");
                }finally {
                    textArea.append("\n");
                }
            }
        });

        ver.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if(id3Tree[0]!=null){
                   textArea.append(id3Tree[0].verification()+"\n");
               }else{
                   textArea.append("请先引入数据集\n");
               }
            }
        });

        exam.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(id3Tree[0]!=null){
                    textArea.append(id3Tree[0].examination()+"\n");
                }else{
                    textArea.append("请先引入数据集\n");
                }
            }
        });

        testOneByOne.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(id3Tree[0]==null){
                    textArea.append("请先引入数据集\n");
                    return;
                }
                try {
                    String[] strings=jTextField3.getText().split(" ");
                    ArrayList<TreeNode> treeNodes=id3Tree[0].testByOne(strings);
                    textArea.append("开始对于单组数据的测试:\n");
                    for (int i = 0; i < treeNodes.size(); i++) {
                        TreeNode tempNode= treeNodes.get(i);
                        if(i==0){
                            textArea.append("根节点的分岔属性为'"+tempNode.getAttribute()+"',");
                            continue;
                        }
                        textArea.append("接着其走向了'"+tempNode.getOutPut() +"'分支,");
                        if(tempNode.getLeafValue()==null){
                            textArea.append("下一个分岔属性为'"+tempNode.getAttribute()+"',");
                        }else{
                            textArea.append("到达了叶子节点,最终的值为'"+tempNode.getLeafValue()+"',");
                        }
                    }
                    if(treeNodes.get(treeNodes.size()-1).getLeafValue().equals(strings[strings.length-1])){
                        textArea.append("并于预期相同.\n");
                    }else{
                        textArea.append("并于预期相悖论,预期的结果为'"+strings[strings.length-1]+"'\n");
                    }
                } catch (SecondException secondException) {
                    textArea.append(secondException.getMessage()+"\n");
                }finally {
                    textArea.append("\n");
                }
            }
        });

        jf.add(text1);
        jf.add(jTextField1);
        jf.add(text3);
        jf.add(jTextField2);
        jf.add(text4);
        jf.add(jTextField3);
        jf.add(pullIn);
        jf.add(ver);
        jf.add(exam);
        jf.add(testOneByOne);
        jf.add(print);
        jf.add(text5);
        jf.add(jPanel);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
