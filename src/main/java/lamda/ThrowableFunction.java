package lamda;

import static java.util.Objects.requireNonNull;

/**
 * @author liqingyun
 * @date 2018/11/23
 */
@FunctionalInterface
public interface ThrowableFunction<T, R, X extends Exception> {

    static <T, X extends Exception> ThrowableFunction<T, T, X> identity() {
        return t -> t;
    }

    R apply(T t) throws X;

    default <V> ThrowableFunction<V, R, X>
            compose(ThrowableFunction<? super V, ? extends T, X> before) {
        requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    default <V> ThrowableFunction<T, V, X>
            andThen(ThrowableFunction<? super R, ? extends V, X> after) {
        requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }
}
