package lamda;

/**
 * @author liqingyun
 * @date 2018/11/23
 */
@FunctionalInterface
public interface ThrowableCallable<T, X extends Exception> {

    T call() throws X;
}
