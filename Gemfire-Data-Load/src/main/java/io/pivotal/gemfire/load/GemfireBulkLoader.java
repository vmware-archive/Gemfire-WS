package io.pivotal.gemfire.load;

import io.pivotal.domain.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;

public class GemfireBulkLoader {

	private static final String FILE_NAME="/Users/nchandrappa/Documents/Gemfire_Preso/Gemfire_Workshop/Gemfire-WS/Gemfire-Data-Load/src/main/resources/data/transaction-data.tsv";
	private static Logger logger = Logger.getLogger("GemfireBulkLoader");

	private static ClientCache getCache() {

		ClientCache cache = new ClientCacheFactory()
		.set("name", "ProjectClient")
		.set("log-level", "info")
		.set("log-file","client.log")
		.set("cache-xml-file",
				"gemfire/config/clientCache.xml").create();

		return cache;
	}

	public static void loadData(int batchSize) {

		Region<String,Transaction> transactionRegion = getCache().getRegion("transaction");
		Map<String, Transaction> buffer = new HashMap<String, Transaction>();

		String line = null;
		BufferedReader bufferedReader = null;
		FileReader fileReader = null;

		long start = System.currentTimeMillis();

		try {
			fileReader = new FileReader(FILE_NAME);
			bufferedReader = new BufferedReader(fileReader);
			int count = 0;

			while ((line = bufferedReader.readLine()) != null) {
				count++;
				String [] attributes = line.split("\\|");
				Transaction transaction = new Transaction();
				objectMapper(attributes, transaction);

				buffer.put(transaction.getKey(), transaction);

				// Batch insert into Gemfire cluster
				if( count % batchSize == 0) {
					transactionRegion.putAll(buffer);
					System.out.println("Inserted " + batchSize + " records");
					buffer.clear();
				}
			}

			bufferedReader.close();

		} catch(Exception e) {
			logger.log(Level.SEVERE, "Exception during Data load");
			e.printStackTrace();
		}

		// there may be existing records to flush so this takes care of it
        if (!buffer.isEmpty())
        {
        	transactionRegion.putAll(buffer);
        	System.out.println("Inserted " + buffer.size() + " records");
            buffer.clear();
        }


       long end = System.currentTimeMillis() - start;
       float elapsedTimeSec = end/1000F;
       logger.log (Level.INFO, String.format("Elapsed time in seconds %f", elapsedTimeSec));


	}

	private static void objectMapper(String[] attributes, Transaction transaction) throws ParseException {

		transaction.setSsn(attributes[0]);
		transaction.setFirstName(attributes[1]);
		transaction.setLastName(attributes[2]);
		transaction.setGender(attributes[3]);
		transaction.setZip(Integer.parseInt(attributes[7]));
		transaction.setPurchaseAmount(Float.parseFloat(attributes[18]));
		transaction.setAccountNumber(Integer.parseInt(attributes[13]));
		transaction.setTransactionNumber(Integer.parseInt(attributes[15]));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
		String dateInString = attributes[16];
		Date transactionDate = sdf.parse(dateInString);

		transaction.setTransactionDate(transactionDate);
		transaction.setCategory(attributes[17]);
		transaction.setKey(transaction.getTransactionNumber() + "_" + transaction.getSsn());

	}

	public static void main(String args[]) {
		GemfireBulkLoader.loadData(20);
		System.exit(0);
	}

}
