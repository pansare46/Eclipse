package com.maven.spring.jdbc;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
//prefer composition over extension.
public class TransactionServiceImpl implements TransactionService {
	
	private static final Logger log = Logger.getLogger(TransactionServiceImpl.class);

	PlatformTransactionManager transactionManager;
	DataSource dataSource;
	TransactionDao daoTransaction;

	
	public void transferAmount(int senderAccountNumber, int receiverAccountNumber, int amountToSend) {
		
		log.info("[transaction proceesing..]");
		TransactionDefinition txDef = new DefaultTransactionDefinition();
	
		TransactionStatus txStatus = transactionManager.getTransaction(txDef);
		
		daoTransaction.doTransfer(senderAccountNumber, receiverAccountNumber, amountToSend);

		try {

			transactionManager.commit(txStatus);
		

		} catch (Exception e) {

			transactionManager.rollback(txStatus);

		}
		
	}
	
	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public static Logger getLog() {
		return log;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public TransactionDao getDaoTransaction() {
		return daoTransaction;
	}

	public void setDaoTransaction(TransactionDao daoTransaction) {
		this.daoTransaction = daoTransaction;
	}




	
}
