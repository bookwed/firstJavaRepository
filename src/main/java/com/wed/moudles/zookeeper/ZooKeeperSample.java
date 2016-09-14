package com.wed.moudles.zookeeper;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 
 * ZooKeeperSample.java
 * @author wed
 * @date 2016年9月12日  下午3:35:27
 * @description
 * 说明：
 * 	1、zab协议
 * 	2、znode 非持久性
 */

public class ZooKeeperSample {

	public static void main(String[] args) {
		String connectString = "";
		int sessionTimeout = 0;
		try {
			ZooKeeper zooKeeper = new ZooKeeper(connectString, sessionTimeout, null);
		
			//创建节点
			zooKeeper.create("/data", "root data".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			
			//删除节点
			zooKeeper.delete("/data", -1);	//版本号-1，匹配所有版本
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * zkClient的基础使用
	 */
	public void zkClientOperate(){
		ZkClient zkClient = new ZkClient("");
		
		String path = "/data";
		//创建节点
		zkClient.createPersistent(path);
		//创建子节点
		zkClient.create(path + "/child", "child node", CreateMode.EPHEMERAL);
		//获取子节点
		List<String> children = zkClient.getChildren(path);
		//获取子节点个数
		int childCount = zkClient.countChildren(path);
		//判断节点是否存在
		zkClient.exists(path);
		//写入数据
		zkClient.writeData(path + "/child", "hello");
		//读取节点数据
		Object obj = zkClient.readData(path + "/child");
		//删除节点
		zkClient.delete(path+"/child");
		
		//wacther机制
		//三种状态：子节点的变化、数据的变化、状态的变化
		
		//1订阅子节点的变化
		zkClient.subscribeChildChanges(path, new IZkChildListener() {
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				
			}
		});

		//2订阅数据的变化
		zkClient.subscribeDataChanges(path, new IZkDataListener() {
			public void handleDataDeleted(String dataPath) throws Exception {
				
			}
			
			public void handleDataChange(String dataPath, Object data) throws Exception {
				
			}
		});
		
		//3订阅节点连接及状态的变化
		zkClient.subscribeStateChanges(new IZkStateListener() {
			
			public void handleStateChanged(KeeperState arg0) throws Exception {
				// TODO Auto-generated method stub
			}
			
			public void handleSessionEstablishmentError(Throwable arg0) throws Exception {
				// TODO Auto-generated method stub
			}
			
			public void handleNewSession() throws Exception {
				// TODO Auto-generated method stub
			}
		});
	}
	
	/**
	 * 基于zookeeper所实现的服务消费者获取服务提供者地址列表的部分关键代码
	 */
	List<String> serverList = new ArrayList<String>();
	public void getServerList(){
		String serviceName = "service-B";
		String zkServerList = "192.168.1.11:2181";
		String service_path = "/configCenter/"+serviceName;	//服务节点路径
		ZkClient zkClient = new ZkClient(zkServerList);
		
		boolean serviceExists = zkClient.exists(service_path);
		if(serviceExists){
			//获取到服务提供者地址列表后，根据负载均衡算法，选出一台服务器，发起远程调用
			serverList = zkClient.getChildren(service_path);
		}else{
			throw new RuntimeException("service not exist");
		}

		//监听：上线、下线、宕机
		zkClient.subscribeChildChanges(service_path, new IZkChildListener() {
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				serverList = currentChilds;
			}
		});
	}
	
	/**
	 * 服务提供者向zookeeper集群注册服务的部分关键代码
	 */
	public void setServerList(){
		String serverList = "192.168.1.11:2181";
		String path = "/configCenter";	//根节点路径
		ZkClient zkClient = new ZkClient(serverList);
		boolean rootExists = zkClient.exists(path);
		if(!rootExists){
			zkClient.createPersistent(path);	//创建根节点
		}
		
		String serviceName = "service-B";
		boolean serviceExists = zkClient.exists(path +"/" + serviceName);
		if(!serviceExists){
			zkClient.createPersistent(path +"/" + serviceName);	//创建服务节点
		}
		try {
			//注册当前服务器，可以在节点的数据里面存放节点的权重
			InetAddress addr = InetAddress.getLocalHost();
			String ip = addr.getHostAddress().toString();	//获取本机ip
			
			//创建当前服务器节点
			zkClient.createEphemeral(path+"/"+serviceName+"/"+ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
