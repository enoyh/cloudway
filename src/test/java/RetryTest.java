import static retry.RetryClient.defaultRetryClient;

import java.io.IOException;

import retry.RetryClient;

/**
 * @author liqingyun
 * @date 2018/11/23
 */
public class RetryTest {

    private static RetryClient retryClient = defaultRetryClient();

    public static void main(String[] args) throws IOException {
        String s = retryClient.call(() -> "a");
        System.out.println(s);
        Integer i = retryClient.call(() -> 0);
        System.out.println(i);
        retryClient.run(() -> {
            throw new IOException();
        });
        System.out.println(i);

    }
}
