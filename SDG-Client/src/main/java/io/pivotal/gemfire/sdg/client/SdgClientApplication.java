package io.pivotal.gemfire.sdg.client;

import io.pivotal.gemfire.sdg.domain.Transaction;
import io.pivotal.gemfire.sdg.repository.TransactionRepo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:spring/config/sdg-client-cache.xml")
public class SdgClientApplication {

	@Autowired
	private TransactionRepo transactionRepo;

	private static Transaction transaction;

	static {
		transaction = new Transaction();
		transaction.setSsn("781-27-1815");
		transaction.setFirstName("Hortensia");
		transaction.setLastName("Kovacek");
		transaction.setGender("F");
		transaction.setAccountNumber(4);
		transaction.setTransactionNumber(2336);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
		String dateInString = "2015-05-01";
		Date transactionDate = null;
		try {
			transactionDate = sdf.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		transaction.setTransactionDate(transactionDate);
		transaction.setCategory("misc_net");
		transaction.setPurchaseAmount(new Float(119.2));
		transaction.setKey(transaction.getTransactionNumber() + "_" + transaction.getSsn());
	}

	public static void main(String[] args) {
		SpringApplication.run(SdgClientApplication.class, args);
	}

	@Bean
    CommandLineRunner keepOpen() {
      return new CommandLineRunner() {
    	  @Override
	      public void run(String... arg0) throws Exception {

    		  // Save
    		  transactionRepo.save(transaction);

    		  // Find By ID
    		  int transactionNumber = 2336;
    		  Transaction transaction = transactionRepo.findByTransactionNumber(transactionNumber);
    		  System.out.println("Transaction Details for Transaction Number: " + transactionNumber);
    		  System.out.println(transaction.toString());

	      }

      };
    }
}
