package core.runners;

import org.apache.commons.lang3.StringUtils;
import org.junit.runners.Parameterized;

public class ConcurrentParametrizedTestRunner extends Parameterized{
    @SuppressWarnings("ucd")
    public ConcurrentParametrizedTestRunner(Class<?> klass) throws Throwable {
        super(klass);
        String threads = "4";
        if (StringUtils.isNotEmpty(System.getProperty("threadCount"))) {
            threads = System.getProperty("threadCount");
        }
        setScheduler(new ParallelScheduler(threads));
    }
}
