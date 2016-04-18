package io.pivotal.gemfire.event.serverside;

import java.util.Properties;

import com.gemstone.gemfire.cache.CacheWriterException;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheWriterAdapter;


public class SampleWriter<K,V> extends CacheWriterAdapter<K,V> implements Declarable {

	@Override
	public void beforeCreate(EntryEvent<K, V> event) throws CacheWriterException{

		boolean writeStatus = false;

		System.out.println("Key is: "+ event.getKey() + "value is: "+event.getNewValue());

		//To-do: logic for writing into Hibernate
		writeStatus = true;

		if(!writeStatus){
			// if write was unsuccessful
			throw new CacheWriterException();
		}

	}

	@Override
	public void beforeUpdate(EntryEvent<K, V> event) throws CacheWriterException{

		boolean writeStatus = false;

		System.out.println("Key is: "+ event.getKey() + "value is: "+event.getNewValue());

		//To-do: logic for writing into Hibernate
		writeStatus = true;

		if(!writeStatus){
			// if write was unsuccessful
			throw new CacheWriterException();
		}

	}

	public void init(Properties arg0) {
		// TODO Auto-generated method stub

	}

}
