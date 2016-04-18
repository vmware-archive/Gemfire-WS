package io.pivotal.gemfire.data.distribution;

public class CustomKey {

	final String countyCode;
	final String transactionKey;

	public CustomKey(String countryCode, String transactionKey) {
		this.countyCode = countryCode;
		this.transactionKey = transactionKey;
	}

	public int hashCode() {
		return transactionKey.hashCode();
	}

	public boolean equals(Object other) {
		if (!(other instanceof CustomKey)) {
			return false;
		}
		return ((CustomKey)other).transactionKey.equals(this.transactionKey);
	}

	public String toString() {
		return transactionKey + ":" + countyCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public String getTransactionKey() {
		return transactionKey;
	}

}
