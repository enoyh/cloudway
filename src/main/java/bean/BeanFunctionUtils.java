package bean;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;

import com.github.phantomthief.util.MoreFunctions;

/**
 * @author liqingyun
 * @date 2018/5/18
 */
public class BeanFunctionUtils {

    public static <T, F> Function<F, T> transBeanFunction(Class<T> c) {
        return origin -> MoreFunctions.throwing(() -> {
            T dest = c.newInstance();
            BeanUtils.copyProperties(dest, origin);
            return dest;
        });
    }

    public static <T, F> List<T> getRecords(List<F> records, Class<T> c) {
        return records.stream() //
                .map(transBeanFunction(c)) //
                .collect(Collectors.toList());
    }
}
