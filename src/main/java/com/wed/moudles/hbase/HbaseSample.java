package com.wed.moudles.hbase;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;



public class HbaseSample {

	public static void main(String[] args) {
		
	}
	private static Configuration conf = null;
	static{
		conf = HBaseConfiguration.create();
		conf.set("hbase.ZooKeeper.property.clientPort", "2181");
		conf.set("hbase.ZooKeeper.quorum", "192.168.137.10");
		conf.set("hbase.master", "192.168.137.10:60000");
	}
	
	/**
	 * 创建表，增加列
	 */
	public static void createTable(){
		String tableName = "user";
		HBaseAdmin hBaseAdmin;
		try {
			hBaseAdmin = new HBaseAdmin(conf);
			if(hBaseAdmin.tableExists(tableName)){
				hBaseAdmin.disableTable(tableName);
				hBaseAdmin.deleteTable(tableName);
			}
			
			HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
			tableDescriptor.addFamily(new HColumnDescriptor("info"));
			tableDescriptor.addFamily(new HColumnDescriptor("class"));
			tableDescriptor.addFamily(new HColumnDescriptor("parent"));
			hBaseAdmin.createTable(tableDescriptor);
			hBaseAdmin.close();
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
