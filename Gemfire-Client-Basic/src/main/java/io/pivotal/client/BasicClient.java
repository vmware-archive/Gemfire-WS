package io.pivotal.client;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;

public class BasicClient {

	private static Region<String, String> getNBORegion() {

		Region<String,String> region = null;

		ClientCache cache = new ClientCacheFactory()
		.set("name", "ProjectClient")
		.set("log-level", "info")
		.set("log-file","client.log")
		.set("cache-xml-file",
				"gemfire/config/clientCache.xml").create();

		region = cache.getRegion("nbo");

		return region;
	}

	public static void main(String args[]) {

		Region<String,String> nboRegion = getNBORegion();

		nboRegion.put("cust1", "Josh");
		nboRegion.put("cust2", "Amey");
		nboRegion.put("cust3", "Nikhil");


		String name = nboRegion.get("cust1");
		System.out.println("Value for key: cust1" + name);

		name = nboRegion.get("cust2");
		System.out.println("Value for key: cust2" + name);
		System.exit(0);
	}

}
