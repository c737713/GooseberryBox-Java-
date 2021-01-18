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
public class MainInterFace extends JFrame {
    final String dataBase1Path="D:\\gitArea\\GooseberryBox-Java-\\decision\\tree\\src\\datasource\\d.txt";
    final String dataBase2Path="D:\\gitArea\\GooseberryBox-Java-\\decision\\tree\\src\\datasource\\e.txt";
    final String TestData="乌黑 稍蜷 沉闷 稍糊 稍凹 硬滑 否";
    JFrame myself;
    Id3Tree id3Tree;
    Integer columnWidth;
    JButton pullIn;
    JButton ver;
    JButton exam;
    JButton testOneByOne;
    JButton print;
    JTextArea textArea;
    JTextField jTextField1;
    JTextField jTextField2;
    JTextField jTextField3;
    ArrayList<JLabel> jLabels;
    JPanel jPanel;
    MyWindow myWindow;

    public MainInterFace() throws HeadlessException {
        super("决策树(id3)");
        this.columnWidth = 35;

        this.setBounds(400,300,380,500);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setResizable(false);
        myself=this;
    }

    public void interfaceInitialization(){
        this.jLabels=new ArrayList<>();
        JLabel text1=new JLabel("输入训练集路径:");
        jLabels.add(text1);
        jTextField1 = new JTextField(this.dataBase1Path,columnWidth);
        JLabel text3=new JLabel("输入测试集路径:");
        jLabels.add(text3);
        jTextField2 = new JTextField(dataBase2Path,columnWidth);
        JLabel text4 =new JLabel("单条内容测试:");
        jLabels.add(text4);
        jTextField3 = new JTextField(this.TestData,columnWidth);
        this.pullIn = new JButton("引入");
        this.ver = new JButton("验证");
        this.exam = new JButton("测试");
        this.testOneByOne = new JButton("逐个测试");
        this.print = new JButton("层序打印");
        JLabel text5 = new JLabel("显示区:                                                                                                             ");
        jLabels.add(text5);
        textArea =new JTextArea("",15,columnWidth);
        jPanel=new JPanel();
        jPanel.add(new JScrollPane(textArea));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        myWindow=new MyWindow(myself);
    }

    public void buttonFunctionInitialization(){
        pullIn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                long  startTime = System.currentTimeMillis();
                try{
                    myWindow.dispose();
                    myWindow.jTextArea.setText(" ");
                    id3Tree=null;
                    Id3Tree tempId3Tree=new Id3Tree(DataBase.getDataBaseByFile(jTextField1.getText()));
                    tempId3Tree.setForTestingData(DataBase.getDataBaseByFile(jTextField2.getText()));
                    id3Tree =tempId3Tree;
                    textArea.append("决策树构建完成,耗费的时间为"+(System.currentTimeMillis()-startTime)+"ms\n");
                }catch (FileNotFoundException e){
                    textArea.append("文件未被找到,请检查路径\n");
                }catch (IOException e){
                    textArea.append("文件输入流发生错误,请检查路径\n");
                }catch (Exception e){
                    textArea.append("发生错误,请检查"+e.getMessage()+"\n");
                }finally {
                    textArea.append("\n");
                }
            }
        });

        ver.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(id3Tree!=null){
                    textArea.append(id3Tree.verification()+"\n");
                }else{
                    textArea.append("请先引入数据集\n");
                }
            }
        });

        exam.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(id3Tree!=null){
                    textArea.append(id3Tree.examination()+"\n");
                }else{
                    textArea.append("请先引入数据集\n");
                }
            }
        });

        testOneByOne.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(id3Tree==null){
                    textArea.append("请先引入数据集\n");
                    return;
                }
                try {
                    String[] strings=jTextField3.getText().split(" ");
                    ArrayList<TreeNode> treeNodes=id3Tree.testByOne(strings);
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

        print.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(id3Tree==null){
                    textArea.append("请先引入数据集\n");
                }else{
                    try{
                        myWindow.jTextArea.append(id3Tree.printTree());
                        myWindow.setVisible(true);
                    }catch (SecondException exception){
                        textArea.append(exception.getMessage()+"\n");
                    }finally {
                        textArea.append("\n");
                    }
                }
            }
        });
    }

    public void ending(){
        this.add(jLabels.get(0));
        this.add(jTextField1);
        this.add(jLabels.get(1));
        this.add(jTextField2);
        this.add(jLabels.get(2));
        this.add(jTextField3);
        this.add(pullIn);
        this.add(ver);
        this.add(exam);
        this.add(testOneByOne);
        this.add(print);
        this.add(jLabels.get(3));
        this.add(jPanel);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
