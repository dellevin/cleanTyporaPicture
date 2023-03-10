
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import java.io.IOException;

public class Show  extends JFrame implements  buttonOperation  {

    public static void main(String[] args) throws FileNotFoundException {

        //引入第三方依赖 JFrame美化
        FlatLightLaf.install();
        try {
            UIManager.setLookAndFeel( new FlatDarkLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
//        String input  = JOptionPane.showInputDialog(null," 输入清理的路径：\n","typora清理程序",JOptionPane.PLAIN_MESSAGE);
//        Main.showInput(input);

        JFrame frmMain = new JFrame("typora无效图片删除程序");
        frmMain.setSize(520, 509);
        frmMain.setLocation(200, 200);
        frmMain.setLayout(new FlowLayout());

        //图片相对路径
        //ImageIcon imageIcon=new ImageIcon("clean.png");
        //frmMain.setIconImage(imageIcon.getImage());

        //文字
        JLabel pathInput = new JLabel(" 输入md文档路径：\n\n");
        //文本框
        JTextArea text = new JTextArea();
        text.setText("");
        text.setBackground(Color.lightGray);//设置文本域背景颜色
        text.setForeground(Color.BLACK);//设置文字颜色
        text.setPreferredSize(new Dimension(300, 20));
        frmMain.add(pathInput);
        frmMain.add(text);

        JLabel temp = new JLabel("                            ");
        temp.setVisible(true);
        frmMain.add(temp);
        //选择文件按钮
        JButton selectbtnOK = new JButton("选择路径");
        selectbtnOK.setPreferredSize(new Dimension(90, 25));
        selectbtnOK.setLocation(50, 50);
        //按钮
        JButton btnOK = new JButton("开始执行");
        btnOK.setPreferredSize(new Dimension(90, 25));
        btnOK.setLocation(50, 50);
        //按钮
        JButton btnSave = new JButton("保存日志");
        btnSave.setPreferredSize(new Dimension(90, 25));
        btnSave.setLocation(50, 50);
        frmMain.add(selectbtnOK);
        frmMain.add(btnOK);
        frmMain.add(btnSave);

        JLabel temp0 = new JLabel("                                 ");
        temp0.setVisible(true);
        frmMain.add(temp0);

        //文字
        JLabel pathOutput = new JLabel(" 日志文件输出路径：\n\n");
        //文本框
        JTextArea logPath = new JTextArea();
        logPath.setText("");
        logPath.setBackground(Color.lightGray);//设置文本域背景颜色
        logPath.setForeground(Color.BLACK);//设置文字颜色
        logPath.setPreferredSize(new Dimension(400, 20));
        frmMain.add(pathOutput);
        frmMain.add(logPath);
        //输出日志
        JTextArea textLog = new JTextArea("打印输出日志", 20, 43);
        textLog.setBackground(Color.lightGray);//设置文本域背景颜色
        textLog.setForeground(Color.BLACK);//设置文字颜色
        textLog.setEditable(false);
        frmMain.add(textLog);
        //给打印日志加个滚动条子
        JScrollPane jScrollPane = new JScrollPane(textLog);
        jScrollPane.setBounds(13, 10, 480, 500);//这个设置了上面文本域就不要设置了，否则滚动条就不会自动适配
        //横竖滚动条常显
        jScrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        frmMain.add(jScrollPane);

        frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭界面同时进程
        frmMain.setVisible(true);//设置窗口可见
        frmMain.setResizable(false);//设置不能调整大小

        //事件监听
        //保存日志文件
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(textLog.getText().equals("打印输出日志"))
                {
                    //若没写路径，点击 开始执行 的时候就会弹出报警框
                    JOptionPane.showMessageDialog(null, "日志还没有呢!", "什么操作？", JOptionPane.ERROR_MESSAGE);
                }
                else
                {   //并保存文件
                    try {
                        buttonOperation.fileSave(buttonOperation.showFileSaveDialog(frmMain,logPath),textLog);

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        //选择文件路径监听
        selectbtnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!text.getText().equals(""))
                {
                    text.setText("");
                }
                buttonOperation.showFileOpenDialog(frmMain, text);
            }
        });
        //确认按钮事件监听
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputPath = text.getText();
                //System.out.println(inputPath);
                if(inputPath.equals(""))
                {
                    //若没写路径，点击 开始执行 的时候就会弹出报警框
                    JOptionPane.showMessageDialog(null, "你还没有选择路径呢!", "什么操作？", JOptionPane.ERROR_MESSAGE);

                }else
                {
                    try {
                        //运行程序
                        Main.showInput(inputPath);
                        //下面写将日志输入到 textLog 文本域中
                        String resultText= String.valueOf(buttonOperation.fileResult("src/log.txt"));
                        //更新文本显示区内容
                        textLog.setText("");//先清空
                        textLog.setText(resultText.toString());//再写入

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });



    }


}



