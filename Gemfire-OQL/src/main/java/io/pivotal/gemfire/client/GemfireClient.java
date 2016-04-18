package io.pivotal.gemfire.client;

import io.pivotal.domain.Transaction;
import io.pivotal.gemfire.query.QueryRepository;

import com.gemstone.gemfire.cache.query.SelectResults;

public class GemfireClient {

	public static void displayResults(SelectResults<Transaction> results, String query) {

		System.out.println("\nTransaction results for:" + query + "\n");
		if(results != null) {
			for(Transaction transaction: results) {
				System.out.println(transaction.toString());
			}
		}

	}

	@SuppressWarnings("unchecked")
	public static void main(String args[]) {

		QueryRepository queryRepo = new QueryRepository();

		// Query 1:
		String query1 = "SELECT * FROM /transaction LIMIT 3";
		SelectResults<Transaction> results = (SelectResults <Transaction>)queryRepo.doQuery(query1);
		displayResults(results, "Query1");


		// Query2: Get Transaction by ID
		int transactionNumber = 1801;
		results = (SelectResults <Transaction>)queryRepo.getTransactionByNumber(transactionNumber);
		displayResults(results, "Query2");

		//Query3: Invoke prepared statement
		transactionNumber = 1452;
		results = (SelectResults <Transaction>)queryRepo.
					getTransactionPreparedStatement(transactionNumber);
		displayResults(results, "Query3");

	}

}
