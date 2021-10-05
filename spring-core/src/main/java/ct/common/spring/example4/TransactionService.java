package ct.common.spring.example4;

public interface TransactionService {

	void transferAmount(int senderAccountNumber, int receiverAccountNumber, int amountToSend);

	
}
