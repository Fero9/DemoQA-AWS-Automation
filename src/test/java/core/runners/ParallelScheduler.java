package core.runners;

import org.junit.runners.model.RunnerScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class ParallelScheduler implements RunnerScheduler {
    private static final Logger logger = LoggerFactory.getLogger(ParallelScheduler.class);

    private ExecutorService executor;

    public ParallelScheduler(String threadCount){
        logger.debug("ThreadPoolScheduler constructor start");

        int threadNumber = Integer.parseInt(threadCount);
        logger.debug("ThreadPoolScheduler number of threads is: " + threadNumber);
        executor = Executors.newFixedThreadPool(threadNumber);

        logger.debug("ThreadPoolScheduler constructor end");
    }

    public void finished() {
        logger.debug("ThreadPoolScheduler finished start");
        executor.shutdown();
        try {
            executor.awaitTermination(500, TimeUnit.MINUTES);
        } catch (InterruptedException exc) {
            throw new RuntimeException(exc);
        }
        logger.debug("ThreadPoolScheduler finished end");
    }

    public void schedule(Runnable childStatement) {
        logger.debug("ThreadPoolScheduler schedule start");
        executor.submit(childStatement);
        logger.debug("ThreadPoolScheduler schedule end");
    }
}
