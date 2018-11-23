package dp.chain;


import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author liqingyun
 * @date 2018/11/22
 */
public abstract class ChainFilter<T> {

    private ChainFilter<T> nextFilter = null;

    protected ChainFilter() {
    }

    /**
     * 调用链入口
     * can not be Override
     */
    public final T filter(T row) {
        T filterRow = doFilter(row);
        if (this.nextFilter != null) {
            return nextFilter.doFilter(filterRow);
        }
        return filterRow;
    }

    /**
     * must be Override
     */
    abstract protected T doFilter(T row);

    public static class ChainFilterBuilder {

        private ChainFilter first;

        private ChainFilterBuilder() {
        }

        public static ChainFilterBuilder newBuilder() {
            return new ChainFilterBuilder();
        }

        public ChainFilter build() {
            checkNotNull(first);
            return first;
        }

        public final ChainFilterBuilder next(ChainFilter filter) {
            synchronized (this) {
                checkNotNull(filter);
                if (first == null) {
                    first = filter;
                    return this;
                }
                ChainFilter pre = first;
                ChainFilter p = first;
                while (p != null) {
                    // 防止循环链表
                    checkArgument(!filter.equals(p));
                    pre = p;
                    p = p.nextFilter;
                }
                pre.nextFilter = filter;
                return this;
            }
        }
    }

    /**
     * @author liqingyun
     * @date 2018/11/22
     */
    public static class EmptyChainFilter<T> extends ChainFilter<T> {

        @Override
        protected T doFilter(T row) {
            return row;
        }
    }
}