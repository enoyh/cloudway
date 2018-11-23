package lamda;

import static com.google.common.base.Throwables.throwIfUnchecked;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import org.slf4j.Logger;

/**
 * @author liqingyun
 * @date 2018/11/23
 */
public class ExceptionRunUtils {

    private static final Logger logger = getLogger(ExceptionRunUtils.class);
    private static final String FAIL_SAFE_MARK = "[fail safe]";

    public static void runCatch(ThrowableRunnable runnable) {
        catching(() -> {
            runnable.run();
            return null;
        }, e -> logger.error(FAIL_SAFE_MARK, e));
    }

    public static void runThrow(ThrowableRunnable runnable) {
        catching(() -> {
            runnable.run();
            return null;
        }, e -> {
            throwIfUnchecked(e);
            throw new RuntimeException(e);
        });
    }

    public static <T> T callCatch(Callable<T> callable) {
        return catching(callable, e -> logger.error(FAIL_SAFE_MARK, e));
    }

    public static <T> T callThrow(Callable<T> callable) {
        return catching(callable, e -> {
            throwIfUnchecked(e);
            throw new RuntimeException(e);
        });
    }

    private static <R, X extends Throwable> R catching(Callable<R> callable,
            Consumer<Throwable> exceptionHandler) throws X {
        try {
            return callable.call();
        } catch (Throwable e) {
            exceptionHandler.accept(e);
            return null;
        }
    }

    public static void main(String[] args) {
        runCatch(() -> {
            throw new IOException();
        });
    }

}
