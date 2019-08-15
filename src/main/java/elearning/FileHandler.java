package elearning;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {
    /**
     * 读取文件到字符串
     * @param path 文件路径
     * @return 文件的文本内容
     */
    public static String ReadFile(String path){
        FileReader fr = null;
        try {
            fr = new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(fr==null)
            return null;
        BufferedReader bReader = new BufferedReader(fr);
        String ss = "";
        StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
        try {
            while ((ss =bReader.readLine()) != null) {//逐行读取文件内容，读取换行符和末尾的空格
                {
                    sb.append(ss);
                        sb.append("\r\n");
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            bReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 按行读取文件到字符串数组
     * @param path 文件路径
     * @return 文件的文本内容
     */
    public static ArrayList<String> ReadFileByLine(String path){
        FileReader fr = null;
        try {
            fr = new FileReader(path);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> list = new ArrayList<>();
        if(fr==null)
            return null;
        BufferedReader bReader = new BufferedReader(fr);
        String line = "";
        try {
            while ((line =bReader.readLine()) != null) {//逐行读取文件内容，读取换行符和末尾的空格
                {
                    list.add(line);
                }
            }
            bReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static ArrayList<String> GetFileNames(File folder)
    {
        ArrayList<String> list = new ArrayList<>();
        if(folder.isDirectory())
        {
            File[] files=folder.listFiles();
            if(files==null)
                return null;
            try {
                for(File file:files)
                    list.add(file.getName());
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        return list;
    }
}
