package retry;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static retry.RetryUtils.callWithRetry;
import static retry.RetryUtils.runWithRetry;

import java.util.function.Predicate;

import lamda.ThrowableCallable;
import lamda.ThrowableRunnable;

/**
 * @author liqingyun
 * @date 2018/11/23
 */
public class RetryClient {

    private int maxRetryTimes;
    private long retryInterval;
    private Predicate<Exception> exceptionChecker;

    private RetryClient(int maxRetryTimes, long retryInterval,
            Predicate<Exception> exceptionChecker) {
        this.maxRetryTimes = maxRetryTimes;
        this.retryInterval = retryInterval;
        this.exceptionChecker = exceptionChecker;
    }

    public static RetryClient defaultRetryClient() {
        return RetryClient.RetryClientBuilder.newBuilder().build();
    }

    public final <T, X extends Exception> T call(ThrowableCallable<T, X> callable) throws X {
        return callWithRetry(maxRetryTimes, retryInterval, callable, exceptionChecker);
    }

    public final <T, X extends Exception> void run(ThrowableRunnable<X> runnable) throws X {
        runWithRetry(maxRetryTimes, retryInterval, runnable, exceptionChecker);
    }

    public static class RetryClientBuilder<T, X extends Exception> {

        private int maxRetryTimes = 3;
        private long retryInterval = 300;
        private Predicate<Exception> exceptionChecker = exception -> true;

        private RetryClientBuilder() {
        }

        public final static RetryClientBuilder newBuilder() {
            return new RetryClientBuilder();
        }

        public RetryClientBuilder withRetryInterval(int retryInterval) {
            this.retryInterval = retryInterval;
            return this;
        }

        public final RetryClientBuilder withMaxRetryTimes(int maxRetryTimes) {
            this.maxRetryTimes = maxRetryTimes;
            return this;
        }

        public final RetryClientBuilder
                withExceptionChecker(Predicate<Exception> exceptionChecker) {
            this.exceptionChecker = exceptionChecker;
            return this;
        }

        public final RetryClient build() {
            checkNotNull(exceptionChecker);
            checkArgument(maxRetryTimes > 0, "illegal maxRetryTimes:%s", maxRetryTimes);
            checkArgument(retryInterval > 0, "illegal retryInterval:%s", retryInterval);
            return new RetryClient(maxRetryTimes, retryInterval, exceptionChecker);
        }

    }

}
