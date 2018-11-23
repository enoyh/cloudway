package lamda;

/**
 * @author liqingyun
 * @date 2018/11/23
 */
@FunctionalInterface
public interface ThrowableRunnable<X extends Exception> {

    void run() throws X;
}
