package io.pivotal.gemfire.data.distribution;

import com.gemstone.gemfire.cache.EntryOperation;
import com.gemstone.gemfire.cache.PartitionResolver;

@SuppressWarnings("rawtypes")
public class CustomPartitionResolver implements PartitionResolver {

	public void close() {
		// TODO Auto-generated method stub

	}

	public String getName() {
		return this.getClass().getName();
	}

	public Object getRoutingObject(EntryOperation op) {
		CustomKey customKey = (CustomKey) op.getKey();
		return customKey.getCountyCode();
	}

}
