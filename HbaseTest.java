package com.lz;

import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class HbaseTest {
    public static void main( String[] args ) {

        int treadNum = 10 ;

        HbaseTestThread hbaseTestThread = new HbaseTestThread(connection);
        for (int i = 0; i < treadNum ; i++) {
            Thread thread=new Thread(hbaseTestThread,"thread"+i);
            thread.start();
        }
        try {
            TimeUnit.SECONDS.sleep(60);
            HbaseTestThread.flag = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // 声明静态配置，配置zookeeper  
    static org.apache.hadoop.conf.Configuration configuration = null;
            static Connection connection = null;
            static {  
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.master", ",192.168.8.107:16010");
        configuration.set("hbase.zookeeper.quorum", "192.168.8.107");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
       
        try {  
            connection = ConnectionFactory.createConnection(configuration);
        } catch (IOException e) {
            e.printStackTrace();  
        }  
    }
          

}

class HbaseTestThread implements Runnable {

    static boolean flag = true ;
    //static int insertNum  = 0;
    Connection connection ;
    ThreadLocal<Integer> insertNum = new  ThreadLocal<Integer> (){
        protected Integer initialValue(){
            return 0 ;
        }
    };
    public HbaseTestThread(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {

        while (flag){

            String rowId =Thread.currentThread().getName() +"-key" + (int)(Math.random()*10000) ;
            String value = (int)Math.random()*10000+"value" ;
            try {
                insertData("BTable" ,rowId,"family" , "name" ,value);
            } catch (Exception e) {
                e.printStackTrace();
            }
            insertNum.set(insertNum.get()+1); ;
        }
        System.out.println("insertNum : "+insertNum.get() + " opt   avg insertNum : " + insertNum.get()/60 + " opt/s");

    }

    /**
     * 添加行列数据数据
     */
    public void insertData(String tableName, String rowId, String familyName,String qualifier, String value) throws Exception {

        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(rowId.getBytes());// 一个PUT代表一行数据，再NEW一个PUT表示第二行数据,每行一个唯一的ROWKEY，此处rowkey为put构造方法中传入的值
        put.addColumn(familyName.getBytes(), qualifier.getBytes(), value.getBytes());// 本行数据的第一列
        table.put(put);
        //System.out.println(rowId +"---" + insertNum.get());
    }


}
