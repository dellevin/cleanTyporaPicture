import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public interface buttonOperation {
    /*
     * 打开文件
     */
    static void showFileOpenDialog(Component parent, JTextArea msgTextArea) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置默认显示的文件夹为当前文件夹
        //fileChooser.setCurrentDirectory(new File("."));

        // 设置文件选择的模式（只文件夹可选）
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(false);

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
            File file = fileChooser.getSelectedFile();

            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            // File[] files = fileChooser.getSelectedFiles();

            msgTextArea.append( file.getAbsolutePath());
        }
    }
    /*
     * 选择文件保存路径
     */
    static String showFileSaveDialog(Component parent, JTextArea msgTextArea) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();
        // 设置打开文件选择框后默认输入的文件名
        fileChooser.setSelectedFile(new File("Log.txt"));
        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showSaveDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"保存", 则获取选择的保存路径
            File file = fileChooser.getSelectedFile();
            //msgTextArea.setText("");
            msgTextArea.append("日志文件保存为:   "+file.getAbsolutePath());
            return  file.getAbsolutePath();
        }
        return null;
    }
    /*
    *写入流
    */
    static StringBuffer fileResult(String fd) throws IOException {
        //字符读入流
        FileReader reader = new FileReader(fd);
        //读入缓冲区
        char[] buffer = new char[1024];
        //读入结果
        StringBuffer result = new StringBuffer();
        //每次读入缓冲区的长度
        int len;
        //从读入流中读取文件内容并形成结果
        while((len = reader.read(buffer)) != -1) {
            result.append(buffer,0,len);
        }
        //关闭读入流
        reader.close();
        return  result;
    }
    static void fileSave(String fileSaveName,JTextArea msgTextArea) throws IOException {
        //字符读入流
        FileWriter fw = new FileWriter(fileSaveName);
        //获取到文本域的内容
        String str=msgTextArea.getText();
        //写入到文件里面
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)==10){
                fw.write(13);//写入\r
                fw.write(10);//写入\n
            }else{
                fw.write(str.charAt(i));
            }
        }
        //关闭流
        fw.close();

    }

}
