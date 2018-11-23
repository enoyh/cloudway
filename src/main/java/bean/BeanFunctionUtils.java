package bean;

import static lamda.ExceptionRunUtils.callThrow;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @author liqingyun
 * @date 2018/5/18
 */
public final class BeanFunctionUtils {

    public static <T, F> Function<F, T> transBeanFunction(Class<T> c) {
        return origin -> callThrow(() -> {
            T dest = c.newInstance();
            BeanUtils.copyProperties(dest, origin);
            return dest;
        });
    }

    public static <T, F> List<T> transBeans(List<F> records, Class<T> c) {
        return records.stream() //
                .map(transBeanFunction(c)) //
                .collect(Collectors.toList());
    }
}
