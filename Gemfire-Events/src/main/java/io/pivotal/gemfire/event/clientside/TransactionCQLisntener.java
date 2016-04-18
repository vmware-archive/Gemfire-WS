package io.pivotal.gemfire.event.clientside;

import io.pivotal.domain.Transaction;

import com.gemstone.gemfire.cache.query.CqEvent;
import com.gemstone.gemfire.cache.query.CqListener;

public class TransactionCQLisntener implements CqListener {

	public void close() {
		System.out.println("TransactionCQClient:Received Close Event");
	}

	public void onError(CqEvent event) {
		System.out.println("TransactionCQClient:Received onError event");
		System.out.println("TransactionCQClient:Throwable: " + event.getThrowable());
	}

	public void onEvent(CqEvent event) {

		System.out.println("\nTransaction Key is:  " + event.getKey());
		Transaction transaction = (Transaction) event.getNewValue();
		System.out.println("Transaction Details: " + transaction);
	}

}
