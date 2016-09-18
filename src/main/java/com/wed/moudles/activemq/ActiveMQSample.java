package com.wed.moudles.activemq;

/**
 * 
 * ActiveMQSample.java
 * @author wed
 * @date 2016年9月18日  下午2:18:24
 * @description
 * 说明：
 * 	
 */
public class ActiveMQSample {
	
	public static void main(String[] args) {

		/*//queue 示例
		String url = "tcp://192.168.137.10:61616";
        String user = null;
        String password = null;
        String queue = "MyQueue";
        
        new Thread(new MessageSender(queue,url,user,password), "Name-Sender").start();
        new Thread(new MessageReceiver(queue,url,user,password), "Name-Receiver").start();*/
		
		
		/*//topic 单个接收者
		String url = "tcp://192.168.137.10:61616";
        String user = null;
        String password = null;
        String topic = "MyTopic";
        new Thread(new TopicSender(topic,url,user,password), "Name-Sender").start();
        new Thread(new TopicReceiverOneThread(topic,url,user,password), "Name-Receiver").start();*/
		
		//topic 多个接收者
		String url = "tcp://192.168.137.10:61616";
        String user = null;
        String password = null;
        String topic = "MyTopic";
        new Thread(new TopicSender(topic,url,user,password), "Name-Sender").start();
        new Thread(new TopicReceiverMultiThread(topic,url,user,password,"thread1"), "Name-Receiver").start();
        new Thread(new TopicReceiverMultiThread(topic,url,user,password,"thread2"), "Name-Receiver").start();
        new Thread(new TopicReceiverMultiThread(topic,url,user,password,"thread3"), "Name-Receiver").start();
	}
}
