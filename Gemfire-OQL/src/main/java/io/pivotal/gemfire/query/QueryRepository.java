package io.pivotal.gemfire.query;

import io.pivotal.domain.Transaction;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.query.FunctionDomainException;
import com.gemstone.gemfire.cache.query.NameResolutionException;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryInvocationTargetException;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.cache.query.TypeMismatchException;

public class QueryRepository {

	private static Logger logger = Logger.getLogger("QueryRepository");
	private static ClientCache cache;

	static {

		cache = new ClientCacheFactory()
		.set("name", "ProjectClient")
		.set("log-level", "info")
		.set("log-file","client.log")
		.set("cache-xml-file",
				"gemfire/config/clientCache.xml").create();

	}

	@SuppressWarnings("unchecked")
	public SelectResults<Transaction> getTransactionByNumber(int transactionNumber) {

		String queryString1 ="SELECT * FROM /transaction t WHERE" +
								" t.transactionNumber = " + transactionNumber;

		return  (SelectResults <Transaction>)doQuery(queryString1);

	}

	@SuppressWarnings({ "unchecked", "unused" })
	public SelectResults<Transaction> getTransactionPreparedStatement(int transactionNumber) {

		String queryString1 ="SELECT * FROM /transaction t WHERE" +
								" t.transactionNumber = $1";

		QueryService queryService = cache.getQueryService();

		// Create the Query Object.
		Query query = queryService.newQuery(queryString1);

		// Set query parameters.
		Object[] params = new Object[1];
		params[0] = transactionNumber;

		SelectResults<Transaction> results = null;
		// Execute Query locally. Returns results set.
		try {
			results = (SelectResults<Transaction>)query.execute(params);
		} catch (FunctionDomainException e) {
			logger.log(Level.SEVERE, "exception while querying");
			e.printStackTrace();
		} catch (TypeMismatchException e) {
			logger.log(Level.SEVERE, "exception while querying");
			e.printStackTrace();
		} catch (NameResolutionException e) {
			logger.log(Level.SEVERE, "exception while querying");
			e.printStackTrace();
		} catch (QueryInvocationTargetException e) {
			logger.log(Level.SEVERE, "exception while querying");
			e.printStackTrace();
		}

		return results;

	}


	public SelectResults<?> doQuery (String queryString) {

		// Get QueryService from Cache.
		QueryService qs = cache.getQueryService();

		// Create the Query Object.
		Query q = qs.newQuery(queryString);

		SelectResults<?> results = null;
		try {
			// Execute Query locally. Returns results set.
			results = (SelectResults<?>)q.execute();
		}
		catch (Exception e) {

			logger.log(Level.SEVERE, "exception while querying");
			e.printStackTrace();
		}
		return results;
	}

}
