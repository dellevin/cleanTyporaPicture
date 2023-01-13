import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class Show  extends JFrame {
    public static void main(String[] args) throws FileNotFoundException {
        String input  = JOptionPane.showInputDialog(null," 输入清理的路径：\n","typora清理程序",JOptionPane.PLAIN_MESSAGE);
        Main.showInput(input);
/*
        JFrame frmMain = new JFrame("图形界面范例");
        frmMain.setSize(500, 720);
        frmMain.setLocation(200, 200);
        frmMain.setLayout(new FlowLayout());
        //文字
        JLabel pathInput = new JLabel(" 输入清理的路径：\n");
        //文本框
        JTextArea text = new JTextArea("");
        text.setText("输入路径");
        text.setPreferredSize(new Dimension(400, 20));
        //按钮
        JButton btnOK = new JButton("确认");
        btnOK.setPreferredSize(new Dimension(60, 25));
        btnOK.setLocation(50, 50);

        //输出日志
        JTextArea textLog = new JTextArea("测试", 20, 43);
        textLog.setPreferredSize(new Dimension(470, 600));


//        char[] charPass = text.getPassword();
//        String pass = String.valueOf(charPass);
//        System.out.println(pass);
        frmMain.add(pathInput);
        frmMain.add(text);
        frmMain.add(btnOK);
        frmMain.add(textLog);


        //事件监听
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println(actionEvent);


            }
        });

        frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmMain.setVisible(true);
        frmMain.setResizable(false);

 */
    }


}



