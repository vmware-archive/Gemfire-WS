package io.pivotal.gemfire.sdg.repository;

import io.pivotal.gemfire.sdg.domain.Transaction;

import java.util.List;

import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "transactions", path = "transactions")
public interface TransactionRepo extends GemfireRepository<Transaction, String> {

	@Query("SELECT * FROM /transaction LIMIT 3")
	List<Transaction> getTransactions();

	Transaction findByTransactionNumber(int transactionNumber);

}
