package com.wed.moudles.activemq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicReceiverOneThread implements Runnable{

	private String url;
    private String user;
    private String password;
    private final String TOPIC;
    
    public TopicReceiverOneThread(String topic,String url, String user, String password) {
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
			session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic(TOPIC);
			MessageConsumer consumer = session.createConsumer(topic);
			consumer.setMessageListener(new MessageListener() {
				public void onMessage(Message message) {
					TextMessage tm = (TextMessage) message;
					try {
						System.out.println("我是topic Receiver,收到消息如下: \r\n"+tm.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			
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
