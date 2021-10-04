package com.maven.spring.jdbc;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.JdbcTemplate;

public class TransactionDao {

	private int account1Balance;
	private int account2Balance;
	private int account1NewBalance;
	private int account2NewBalance;

	private boolean account;

	private String updateAccountBalanceQuery = "UPDATE ACCOUNT SET BALANCE = ? WHERE accountNumber = ?";
	private String getAccountBalanceQuery = "SELECT BALANCE FROM ACCOUNT WHERE accountNumber = ?";
	private String accountVerificationQuery = "SELECT 1 FROM ACCOUNT WHERE accountNumber = ?";

	private static final Logger log = Logger.getLogger(TransactionDao.class);

	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;;

	public void doTransfer(int senderAccountNumber, int receiverAccountNumber, int amountToSend) {

		verifyAccount(senderAccountNumber);

		verifyAccount(receiverAccountNumber);

		log.info("[Transfer amount: " + amountToSend + "]");

		if (amountToSend <= 0) {
			log.info("[Input Value is less than 0]");
			throw new RuntimeException(" validation failed");
		}

		account1Balance = getAccountBalance(senderAccountNumber);

		if (account1Balance < amountToSend && account1Balance <= 0) {
			log.info("[Cannot transfer, account doesn't have enough funds!]");
			throw new RuntimeException(" validation failed");
		}

		account1NewBalance = account1Balance - amountToSend;

		updateAccountBalance(senderAccountNumber, account1NewBalance);

		account2Balance = getAccountBalance(receiverAccountNumber);

		account2NewBalance = account2Balance + amountToSend;

		updateAccountBalance(receiverAccountNumber, account2NewBalance);

	}

	private void updateAccountBalance(int accountNumber, int amountToUpdate) {
		jdbcTemplate.update(updateAccountBalanceQuery, amountToUpdate, accountNumber);
	}

	private int getAccountBalance(int accountNumber) {
		return jdbcTemplate.queryForObject(getAccountBalanceQuery, new Object[] { accountNumber }, Integer.class);
	}

	private void verifyAccount(int accountNumber) {

		account = jdbcTemplate.queryForObject(accountVerificationQuery, new Object[] { accountNumber },
				Integer.class) == 1;

		if (!account) {
			log.info("[Account " + accountNumber + " doesn't exists]");
			throw new RuntimeException("verification fail !");
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
