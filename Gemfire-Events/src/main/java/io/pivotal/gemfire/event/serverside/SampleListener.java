package io.pivotal.gemfire.event.serverside;

import java.util.Properties;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;

public class SampleListener<K, V> extends CacheListenerAdapter<K, V> implements Declarable{

	@Override
	public void afterCreate(EntryEvent<K, V> e){
		System.out.println("Created " + e.getKey());
	}

	@Override
	public void afterUpdate(EntryEvent<K, V> e){
		System.out.println("Updated: Key= " + e.getKey() +
				" value= " + e.getNewValue());
	}

	public void init(Properties arg0) {
		// TODO Auto-generated method stub

	}

}
