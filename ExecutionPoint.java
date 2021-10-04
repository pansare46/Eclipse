package com.maven.spring.jdbc;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ExecutionPoint {

	private int senderAccountNumber = 123456;
	private int receiverAccountNumber = 123654;
	private int amountToSend = 5;
	
	

	private static final Logger log = Logger.getLogger(ExecutionPoint.class);

	public static void main(String[] args) {
		ExecutionPoint executionObj = new ExecutionPoint();
		
		try {
		log.info("[ context initializing.. ]");
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Transaction.xml");
		TransactionService service = (TransactionService) context.getBean("fundTransferTran");

			service.transferAmount(executionObj.senderAccountNumber, executionObj.receiverAccountNumber,executionObj.amountToSend);
			
		} catch (Throwable e) {
			log.info("[ Exception in Program ",e);
			
		}

	}

}
