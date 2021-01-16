package interaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Mr Chippy
 */
public class MyWindow extends Dialog {
    JButton jButton;
    JTextArea jTextArea;
    JPanel jPanel;

    public MyWindow(Frame owner) {
        super(owner, "决策树展示窗口");
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBounds(300,200,800,300);
        jButton=new JButton("退出");
        jTextArea=new JTextArea(12,80);
        jPanel=new JPanel();
        this.add(jButton);
        jPanel.add(new JScrollPane(jTextArea));
        this.add(jPanel);
        jButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                jTextArea.setText(" ");
            }
        });
    }
}
