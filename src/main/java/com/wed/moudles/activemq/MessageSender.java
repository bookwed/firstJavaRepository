package com.wed.moudles.activemq;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageSender implements Runnable{

	private String url;
    private String user;
    private String password;
    private final String QUEUE;
    
	public MessageSender(String queue, String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.QUEUE = queue;
    }
	
	public void run() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
		Connection connection = null;
		Session session = null;
		Destination sendQueue;
		int messageCount = 0;
		
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			while(true){
				session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
				sendQueue = session.createQueue(QUEUE);
				MessageProducer producer = session.createProducer(sendQueue);
				TextMessage outMessage = session.createTextMessage();
				outMessage.setText(new Date() + "现在发送是第" + messageCount + "条消息");
				producer.send(outMessage);
				session.commit();
				producer.close();
				if ((++messageCount) == 10) {
                    // 发够十条消息退出
                    break;
                }
				Thread.sleep(1000);
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
