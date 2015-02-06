package main;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer implements MessageListener {

	public static void main(String[] args) throws Exception {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("testQueue");
		MessageProducer producer = session.createProducer(destination);
		MessageConsumer consumer = session.createConsumer(destination);
		consumer.setMessageListener(new Producer());

		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			Message mes = session.createTextMessage(in.next());
			producer.send(mes);
		}

		connection.close();
	}

	@Override
	public void onMessage(Message msg) {
		try {
			System.out.println(((TextMessage) msg).getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
