package web;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 * @author liqingyun
 * @date 2018/5/16
 */
public class CrmCommonView {

    private String result;
    private String message;

    public CrmCommonView(String code) {
        this.result = code;
        this.message = ResourceBundleUtils.getMessage(code);
    }

    public String getMessage() {
        return message;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
    }

}
