package com.maven.spring.jdbc;

public interface TransactionService {
	void transferAmount(int senderAccountNumber, int receiverAccountNumber, int amountToSend);

}
