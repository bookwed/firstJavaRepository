package com.wed.moudles.activemq;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicSender implements Runnable{

	private String url;
    private String user;
    private String password;
    private final String TOPIC;
    
    public TopicSender(String topic,String url, String user, String password) {
    	this.url = url;
        this.user = user;
        this.password = password;
        this.TOPIC = topic;
    }
    
    public void run() {
    	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
		Connection connection = null;
		Session session = null;
		
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic(TOPIC);
			MessageProducer producer = session.createProducer(topic);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);		//不持久化
			for (int i = 0; i < 10; i++) {
				TextMessage message = session.createTextMessage(new Date()+" 第" + i +"条topic消息： hello topic!");
				producer.send(message);
				Thread.sleep(1000);
			}
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(null != connection){
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
    }
    
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
