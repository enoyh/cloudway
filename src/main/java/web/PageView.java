package web;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.util.List;

/**
 * @author liqingyun
 * @date 2018/5/16
 */
public class PageView<E> extends CommonView {

    private final List<E> list;
    private final int currentPage;
    private final int totalPage;
    private final int countPerPage;
    private final int totalCount;

    private PageView(List<E> list, int currentPage, int totalPage, int countPerPage,
            int totalCount) {
        super("1");
        this.list = list;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.countPerPage = countPerPage;
        this.totalCount = totalCount;
    }

    public static <E> PageView<E> of(List<E> list, int currentPage, int countPerPage,
            int totalCount) {
        int totalPage = (int) Math.ceil((double) totalCount / countPerPage);
        return new PageView(list, currentPage, totalPage, countPerPage, totalCount);
    }

    public List<E> getList() {
        return list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getCountPerPage() {
        return countPerPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    @Override
    public String toString() {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
    }

}
