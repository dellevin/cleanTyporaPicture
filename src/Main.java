import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
* md文件，无效图片检测
*/
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        //输入md文件存在路径
        System.out.print("写出md文件存在的路径：");
        Scanner sc=new Scanner(System.in);
        String pathtemp = sc.nextLine();
        String path = pathtemp;
        //System.out.println(path);
        //String path = "D:\\OneDriveData\\技术笔记\\C_C++_C Sharp";
        PrintStream ps = new PrintStream("src/log.txt");
        System.setOut(ps);
        File file = new File(path);
        recursivelyThroughAndDelete(file);
    }

    public static void showInput(String inputString) throws FileNotFoundException {
        File directory = new File("");
        //获取当前路径
        File pathTemp = new File(directory.getAbsolutePath() + "\\src");
        //创建文件夹
        pathTemp.mkdir();
        //写入日志文件
        PrintStream ps = new PrintStream(directory.getAbsolutePath()+"\\src\\log.txt");
        System.setOut(ps);
        String path = inputString;
        //System.out.println(path);
        //String path = "D:\\OneDriveData\\技术笔记\\C_C++_C Sharp";
        File file = new File(path);
        recursivelyThroughAndDelete(file);
    }
    public static void recursivelyThroughAndDelete(File dir) throws FileNotFoundException {
        // 获取子文件和目
        File[] files = dir.listFiles();
        if (files.length <= 0) {
            return;
        }
        //递归遍历子目录，子文件
        /*
        判断：当是文件时，添加到List中，等待遍历
              当是目录时，递归调用此方法，遍历下一级目录
         */
        //需要遍历图片使用的md文件list
        List<File> list = new ArrayList<>();
        for (File file : files) {
            //是文件保存到ArrayList中，等待读取文件
            if (file.getName().toLowerCase().equals("assets")
                    || file.getName().toLowerCase().equals("assests")
                    || file.getName().toLowerCase().endsWith("git")) { //不需要遍历的文件夹
                continue;
            } else if (file.isFile()) {
                //File filepath= new File(ToDBC(String.valueOf(file)));
                list.add(file);
            } else {
                //递归遍历文件夹
                recursivelyThroughAndDelete(file);
            }
        }
        System.out.println("--------------------typora无效图片删除程序--------------------");
        System.out.println("检测目录       ：" + files[0].getParent());
        System.out.println("目录下的文件如下：" + list);
        System.out.println("*********************【md 图片文件存放路径】*********************");
        //每一层遍历结束，调用清理无用图片方法。
        deleteUselessPicture(dir);
    }

    public static void deleteUselessPicture(File dir) throws FileNotFoundException {
        String temp01 = null;
        String temp02[] =null;
        File[] files = dir.listFiles();
        if (files.length <= 0) {
            return;
        }
        //1.遍历assets以及assests文件，将目录下的图片读取出来  list1
        List<File> pictureList1 = new ArrayList<>();
        //2.读取所有md文件，将图片地址读取+解析出来  list2 (该解析一点要准确，文件名转为小写字母)
        List<String> pathList2 = new ArrayList<>();

        //System.out.println("---------------"+dir.listFiles()[0]);
        File fileTest = dir.listFiles()[0];
        //System.out.println( Arrays.stream(fileTest.listFiles()).count() );
        File fileTest01;
        long count = Arrays.stream(fileTest.listFiles()).count();
        //System.out.println(count);  md文件个数
        for (long i = 0; i < count; i++) {//通过文件个数判断循环次数
            fileTest01 = fileTest.listFiles()[(int)i];
            //System.out.println(fileTest01.getName());
            for (File file : files) {
                //System.out.println(file.isFile());
                //System.out.println(file.getName());
                //是文件保存到ArrayList中，等待读取文件
                if (file.isDirectory()) {
                    for (File listFile : fileTest01.listFiles()) {
                        String regexmd = ".*\\.(ai|apng|avif|bmp|cdr|dxf|eps|exif|fpx|gif|jfif|jpg|jpeg|pcd|pcx|png|psd|raw|svg|tga|tif|ufo|webp|wmf)$"; //图片格式结尾
                        String fileName = listFile.getName().toLowerCase();
                        Pattern p = Pattern.compile(regexmd);
                        Matcher m = p.matcher(fileName); // 获取 matcher 对象
                        if (m.matches()) {
                            //System.out.println(listFile);
                            pictureList1.add(listFile);
                        }
                    }

                } else if (file.isFile()) { //只统计md文档目前没想出全角半角的判断
                    InputStreamReader inputStreamReader = null;
                    try {
                        inputStreamReader = new InputStreamReader(new FileInputStream(file));
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String readEachLine;
                        while ((readEachLine = bufferedReader.readLine()) != null) {
                            String pathString = patch(readEachLine.toLowerCase());

                            if (pathString.length() > 0) {
                                //System.out.println(pathString);
                                temp01=pathString.toLowerCase();
                                temp02 = temp01.split("/",2);
                                //System.out.println(temp02[1]);
                                pathList2.add(temp02[1]);
                                //System.out.println(temp01);
                                //pathList2.add(pathString.toLowerCase());
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(fileTest01.getName()+".md   文件图片存储路径为  ："+files[0].getParent()+"\\assets\\"+fileTest01.getName());


        }
        System.out.println("*********************【md 图片文件存放路径】*********************");

        //System.out.printf("asset或assest目录下所有待查询图片有%d个，明细list1为:%s\n", pictureList1.size(), pictureList1);
        //System.out.printf("md文件中出现的图片有%d个，明细list2为:%s\n", pathList2.size(), pathList2);

        //3.遍历list1，将不在list2中的图片删除 (高风险 操作 )
        //记录并打印使用中的图片明细
        List<String> listUsing = new ArrayList<>();
        //记录并打印未使用的图片明细(已删除图片明细)
        List<String> listUseless = new ArrayList<>();
        int countMark = 0;
        for (File file : pictureList1) {
            String name = file.getName();
            if (pathList2.contains(name.toLowerCase())) {
                listUsing.add(name);
            } else if (!pathList2.contains(name.toLowerCase())) {
                listUseless.add(name);
                countMark++;
//              打印日志
                System.out.println("检测到 "+  file.getName() + " 图片未使用，已移动到该图片目录下 无效图片 文件夹");
                //System.out.println(file.getParent()+"\\"+file.getName());
                //移动无效图片到 无效图片 文件夹
                File fileMakeDir = new File(file.getParent()+"\\无效图片\\");
                fileMakeDir.mkdir();
                File oldName = new File(file.getParent()+"\\"+file.getName());
                File newName = new File(file.getParent()+"\\无效图片\\"+file.getName());
                oldName.renameTo(newName);
                //移动完毕后删除无效的图片
                file.delete();

            }
        }

        if(countMark==0)
        {
            System.out.println("→→→→→→→你还挺细心，没有重复图片");
        }
        //使用及未使用明细
        //System.out.printf("对比后%d个图片使用中明细：%s；\n对比后%d个图片已删除明细：%s\n", listUsing.size(), listUsing, listUseless.size(), listUseless);
        // 最终统计结果

        System.out.printf("→  目录 %s 遍历完成,遍历 %d 个图片; %d 个图片仍在使用,删除 %d 个图片  ←\n",
                files[0].getParent(), pictureList1.size(), listUsing.size(), listUseless.size());
        System.out.println("--------------------typora无效图片删除程序--------------------");
        System.out.println();
        System.out.println();
    }

    public static String patch(String input) {
        String regexStart = "(assets|assests)\\/";  //assets/或者 assest/开头   .图片格式结尾
        String regexEnd = "(assets|assests)\\/.*\\.(ai|apng|avif|bmp|cdr|dxf|eps|exif|fpx|gif|jfif|jpg|jpeg|pcd|pcx|png|psd|raw|svg|tga|tif|ufo|webp|wmf)"; //将多种文件格式 融入进正则表达式
        //规则过滤
        Pattern p1 = Pattern.compile(regexStart);
        Matcher m1 = p1.matcher(input); // 获取 matcher 对象
        Pattern p2 = Pattern.compile(regexEnd);
        Matcher m2 = p2.matcher(input); // 获取 matcher 对象
        int strStart = 0;
        int strEnd = 0;
        if (m1.find() && m2.find()) {
            strStart = m1.end();
            strEnd = m2.end();
        }
        String strRes = input.substring(strStart, strEnd);
        return strRes;
    }
}
