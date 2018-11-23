package retry;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.function.Predicate;

import org.slf4j.Logger;

import lamda.ThrowableCallable;
import lamda.ThrowableRunnable;

/**
 * @author liqingyun
 * @date 2018/11/23
 */
class RetryUtils {

    private static Logger logger = getLogger(RetryUtils.class);

    private RetryUtils() {
        throw new UnsupportedOperationException();
    }

    public static <X extends Exception> void runWithRetry(int maxRetryTimes, long retryInterval,
            ThrowableRunnable<X> run) throws X {
        runWithRetry(maxRetryTimes, retryInterval, run, exception -> true);
    }

    public static <X extends Exception> void runWithRetry(int maxRetryTimes, long retryInterval,
            ThrowableRunnable<X> func, Predicate<Exception> exceptionChecker) throws X {
        callWithRetry(maxRetryTimes, retryInterval, () -> {
            func.run();
            return null;
        }, exceptionChecker);
    }

    public static <T, X extends Exception> T callWithRetry(int maxRetryTimes, long retryInterval,
            ThrowableCallable<T, X> callable) throws X {
        return callWithRetry(maxRetryTimes, retryInterval, callable, exception -> true);
    }

    public static <T, X extends Exception> T callWithRetry(int maxRetryTimes, long retryInterval,
            ThrowableCallable<T, X> callable, Predicate<Exception> exceptionChecker) throws X {
        int times = 0;
        Exception exception;
        do {
            try {
                return callable.call();
            } catch (Exception e) {
                if (!exceptionChecker.test(e)) {
                    throw e;
                } else {
                    if (retryInterval > 0) {
                        sleepUninterruptibly(retryInterval, MILLISECONDS);
                    }
                    times++;
                    if (times <= maxRetryTimes) {
                        logger.warn("try to retry for exception:{}, retry times:{}", e.toString(),
                                times);
                    }
                    exception = e;
                }
            }
        } while (times <= maxRetryTimes);
        throw (X) exception;
    }

}
