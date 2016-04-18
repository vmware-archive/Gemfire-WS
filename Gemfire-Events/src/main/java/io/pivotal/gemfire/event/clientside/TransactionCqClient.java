package io.pivotal.gemfire.event.clientside;

import io.pivotal.domain.Transaction;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gemstone.gemfire.cache.TimeoutException;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.query.CqAttributes;
import com.gemstone.gemfire.cache.query.CqAttributesFactory;
import com.gemstone.gemfire.cache.query.CqClosedException;
import com.gemstone.gemfire.cache.query.CqException;
import com.gemstone.gemfire.cache.query.CqExistsException;
import com.gemstone.gemfire.cache.query.CqQuery;
import com.gemstone.gemfire.cache.query.QueryInvalidException;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.RegionNotFoundException;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.cache.query.Struct;

public class TransactionCqClient {

	private static Logger logger = Logger.getLogger("TransactionCqClient");
	private static ClientCache cache;

	static {
		cache = new ClientCacheFactory()
		.set("name", "ProjectClient")
		.set("log-level", "info")
		.set("log-file","client.log")
		.set("cache-xml-file",
				"gemfire/client/clientCache.xml").create();
	}

	public static void displayResults(SelectResults<Transaction> results, String query) {

		System.out.println("\nTransaction results for:" + query + "\n");
		if(results != null) {
			for(Transaction transaction: results) {
				System.out.println(transaction.toString());
			}
		}

	}

	public static void closeCache() throws IOException, TimeoutException {
		cache.close();
	}

	@SuppressWarnings("rawtypes")
	public static void registerCq() throws QueryInvalidException,
			CqExistsException, CqException, CqClosedException, RegionNotFoundException {

		QueryService queryService = cache.getQueryService();

		CqAttributesFactory cqAf = new CqAttributesFactory();
		cqAf.addCqListener(new TransactionCQLisntener());
		CqAttributes cqAttributes = cqAf.create();

		String query = "SELECT * FROM /transaction t WHERE t.purchaseAmount > 300.0";


		// Creating the continuous query and execute it. If executing
		// with initial results, capture
		// the results and iterate over them

		CqQuery myCq;
		myCq = queryService.newCq("purchaseAmtCQ", query, cqAttributes);

		logger.info("Made new CQ Service");

		SelectResults sResults;
		//sResults = myCq.execute();
		sResults = myCq.executeWithInitialResults();

		System.out.println("\nTransaction results for:" + query + "\n");

		for(Object resultObject: sResults) {
			Struct s = (Struct) resultObject;
			Transaction transaction = (Transaction) s.get("value");
			System.out.println(transaction.toString());
		}
	}

	public static void main(String args[]) {

		try {
			TransactionCqClient.registerCq();

			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter 'exit' to end");

			for(;;) {
				if(scanner.hasNext()) {
					String command = scanner.next();
					if(command.equalsIgnoreCase("EXIT")) {
						TransactionCqClient.closeCache();
						scanner.close();
						System.exit(0);
					}
				}
			}

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Exception during CQ Processing.");
			e.printStackTrace();
		}

	}
}
