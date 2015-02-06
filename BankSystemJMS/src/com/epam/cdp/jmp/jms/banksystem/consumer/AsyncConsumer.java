package com.epam.cdp.jmp.jms.banksystem.consumer;

import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.epam.cdp.jmp.jms.banksystem.admin.AdminService;
import com.epam.cdp.jmp.jms.banksystem.admin.JDBCAdminDAO;
import com.epam.cdp.jmp.jms.banksystem.auth.AuthService;
import com.epam.cdp.jmp.jms.banksystem.auth.JDBCAuthDAO;
import com.epam.cdp.jmp.jms.banksystem.customer.CustomerService;
import com.epam.cdp.jmp.jms.banksystem.customer.JDBCCustomerDAO;
import com.epam.cdp.jmp.jms.banksystem.dto.Account;
import com.epam.cdp.jmp.jms.banksystem.dto.User;

public class AsyncConsumer implements MessageListener {

	private MessageProducer producer = null;
	private Session session = null;

	public AsyncConsumer(Destination destination, Session session) {
		this.session = session;
		try {
			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(this);
			producer = session.createProducer(destination);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(Message msg) {
		try {
			String mesText = ((TextMessage) msg).getText();
			String[] params = mesText.split("/");
			AuthService authService = new AuthService(new JDBCAuthDAO());
			if ("acc_list".equals(params[0])) {
				if (params.length < 3) {
					TextMessage response = session.createTextMessage("Wrong params number. Format is 'acc_list/login/pass'");
					producer.send(response);
					return;
				}
				User user = authService.signIn(params[1], params[2]);
				if (user == null) {
					TextMessage response = session.createTextMessage("User is not exist");
					producer.send(response);
					return;
				}
				AdminService adminService = new AdminService(new JDBCAdminDAO());
				List<Account> accountList = adminService.readUserAccountList(user.getId());
				String message = "You are logged in as " + user.getFirstName() + " " + user.getLastName() + "\n";
				message += "Your account list is:\n";
				message += "_____________________\n";
				message += "Currency\tValue\tId\n";
				message += "_____________________\n";
				for (Account account : accountList) {
					message += account.getCurrency().getType() + "\t" + Math.round(account.getValue()) + "\t" + account.getId() + "\n";
				}
				TextMessage response = session.createTextMessage(message);
				producer.send(response);
			} else if ("exchange".equals(params[0])) {
				if (params.length < 6) {
					TextMessage response = session.createTextMessage("Wrong params number. Format is exchange/login/pass/id_from/id_to/value");
					producer.send(response);
					return;
				}
				User user = authService.signIn(params[1], params[2]);
				if (user == null) {
					TextMessage response = session.createTextMessage("User is not exist");
					producer.send(response);
					return;
				}
				CustomerService customerService = new CustomerService(new JDBCCustomerDAO(), new JDBCAdminDAO());
				long fromId = Long.parseLong(params[3]);
				long toId = Long.parseLong(params[4]);
				double value = Double.parseDouble(params[5]);
				customerService.performExchange(fromId, toId, value);
				TextMessage response = session.createTextMessage("Exchange is completed");
				producer.send(response);
			} else {
				TextMessage response = session.createTextMessage("Action is not supported");
				producer.send(response);
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
