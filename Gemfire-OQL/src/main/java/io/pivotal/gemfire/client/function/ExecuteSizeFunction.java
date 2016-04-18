package io.pivotal.gemfire.client.function;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.execute.Execution;
import com.gemstone.gemfire.cache.execute.FunctionService;
import com.gemstone.gemfire.cache.execute.ResultCollector;

public class ExecuteSizeFunction {
    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    public static final String SIZE_FUNCTION_ID = "size-function";
    private ClientCache cache = null;

    public ExecuteSizeFunction() {
        ClientCacheFactory ccf = new ClientCacheFactory();
        ccf.set("cache-xml-file", "gemfire/config/clientCache.xml");
        cache = ccf.create();
    }

    @SuppressWarnings("rawtypes")
	public void run(String regionName) {
        Execution execution = FunctionService.onServer(this.cache).withArgs("/" + regionName);

		ResultCollector collector = execution.execute(SIZE_FUNCTION_ID);

        logger.log (Level.INFO,
                String.format("Region %s contains %s entries",
                        "/" + regionName, ((List) collector.getResult()).get(0)));
    }

    public static void main(String[] args) {
        ExecuteSizeFunction test = new ExecuteSizeFunction();
        test.run("transaction");
    }
}
