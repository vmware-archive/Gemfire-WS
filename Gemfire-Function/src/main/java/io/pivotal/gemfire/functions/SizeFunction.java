package io.pivotal.gemfire.functions;

import java.util.Properties;

import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.GemFireCache;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;

public class SizeFunction extends FunctionAdapter implements Declarable {
    private static final long serialVersionUID = 1L;

    public static final String ID = "size-function";

    private GemFireCache cache;

    public SizeFunction() {
        this.cache = CacheFactory.getAnyInstance();
    }

    @SuppressWarnings("rawtypes")
	public void execute(FunctionContext context) {
        String regionName = (String) context.getArguments();
        Region region = this.cache.getRegion(regionName);
        System.out.println("Getting size of region " + region.getFullPath());
        context.getResultSender().lastResult(region.size());
    }

    public String getId() {
        return ID;
    }

    public void init(Properties properties) {
    }
}
