package com.lz;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * CSV操作(读取和写入)
 *
 * @author lq
 * @version 2018-04-23
 */
public class CSVUtils {

    /**
     * 读取
     *
     * @param file     csv文件(路径+文件名)，csv文件不存在会自动创建
     * @param dataList 数据
     * @return
     */
    public static boolean exportCsv(File file, List<String> dataList) {
        boolean isSucess = false;

        FileOutputStream out = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            FileWriter fileWriter = new FileWriter("ljq.csv", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out = new FileOutputStream(file,true);
            osw = new OutputStreamWriter(out, "GBK");
            bw = new BufferedWriter(osw);
            if (dataList != null && !dataList.isEmpty()) {
                for (String data : dataList) {
                    bw.append(data);
                    bw.append("\r");
                }
            }
            isSucess = true;
        } catch (Exception e) {
            isSucess = false;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                    bw = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (osw != null) {
                try {
                    osw.close();
                    osw = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return isSucess;
    }

    /**
     * 写入
     *
     * @param file csv文件(路径+文件)
     * @return
     */
    public static List<String> importCsv(File file) {
        List<String> dataList = new ArrayList<String>();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                dataList.add(line);
            }
        } catch (Exception e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                    br = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return dataList;
    }


    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        exportCsv();
        //importCsv();
    }


    /**
     * CSV读取测试
     *
     * @throws Exception
     */
    public static void importCsv() {
        List<String> dataList = CSVUtils.importCsv(new File("D:/test/ljq.csv"));
        if (dataList != null && !dataList.isEmpty()) {
            for (int i = 0; i < dataList.size(); i++) {
                if (i != 0) {//不读取第一行
                    String s = dataList.get(i);
                    System.out.println("s  " + s);
                    String[] as = s.split(",");
                    System.out.println(as[0]);
                    System.out.println(as[1]);
                    System.out.println(as[2]);
                }
            }
        }
    }

    /**
     * CSV写入测试
     *
     * @throws Exception
     */
    public static void exportCsv() {
        List<String> dataList = new ArrayList<String>();
        dataList.add("number,name,sex");
        dataList.add("1,张三,男");
        dataList.add("2,李四,男");
        dataList.add("3,小红,女");
        boolean isSuccess = CSVUtils.exportCsv(new File("D:/ljq.csv"), dataList);
        System.out.println(isSuccess);
    }

    public static void readConf(){
        Properties properties = new Properties();
        try {
            // InputStream ins = HbaseTest.class.getResourceAsStream("D://dbconfig.properties");
            InputStream in=new FileInputStream(new File(System.getProperty("user.dir")+File.separator+"dbconfig.properties"));
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
