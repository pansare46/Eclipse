package ct.common.spring.example4;

import org.apache.log4j.Logger;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Startup {

		private int senderAccountNumber = 123456;
		private int receiverAccountNumber = 123654;
		private int amountToSend = 5;

		private static final Logger log = Logger.getLogger(Startup.class);

		public static void main(String[] args) {
			Startup executionObj = new Startup();

			try {
				log.info("[ context initializing.. ]");

				ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.example4.xml");
				TransactionService service = (TransactionService) context.getBean("fundTransferTran");

				service.transferAmount(executionObj.senderAccountNumber, executionObj.receiverAccountNumber,
						executionObj.amountToSend);

				System.out.println("Completed Processing..");

			} catch (Throwable e) {
				log.info("[ Exception in Program ", e);

			}

		}

	

}
