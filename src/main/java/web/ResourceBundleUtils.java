package web;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceBundleUtils {

    private static final String MESSAGE_PREFIX = "message";
    private static Logger logger = LoggerFactory.getLogger(ResourceBundleUtils.class);
    private static ConcurrentMap<Locale, ResourceBundle> bundles = new ConcurrentHashMap<>();

    private static ResourceBundle getBundle(Locale locale) {
        //        locale = LanguageUtils.chooseLocale(locale);
        return bundles.computeIfAbsent(locale, l -> ResourceBundle.getBundle(MESSAGE_PREFIX, l));
    }

    private static String getMessageFromBundle(String key, Locale locale) {
        return Optional.ofNullable(getBundle(locale).getString(key)).orElse(StringUtils.EMPTY);
    }

    public static String getMessage(String key) {
        return getMessage(key, locale());
    }

    private static Locale locale() {
        return null;// TODO
    }

    public static String getMessage(String key, Locale locale) {
        return getMessageWrapped(key, locale, ResourceBundleUtils::getMessageFromBundle);
    }

    private static String getMessageWrapped(String key, Locale locale,
            BiFunction<String, Locale, String> getter) {
        String message = "";
        try {
            message = getter.apply(key, locale);
        } catch (Throwable e) {
            logger.debug("get message err:{}, locale:{}", key, locale);
        }
        return message;
    }

}
