package sql;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author liqingyun
 * @date 2018/5/31
 */
public class SqlGenerator {

    public static String generateUpdateSql(String tableName, Set<String> fields) {
        String update = "update " + tableName + " set ";
        String setField = fields.stream().filter(s -> !s.equals("id")).map(s -> s + "=:" + s)
                .collect(Collectors.joining(", "));
        return update + setField + " where id=:id";
    }

    public static String generateInsertSql(String tableName, Set<String> fields) {
        String insert = "insert into " + tableName + " (";
        String insertField = fields.stream().sorted().collect(Collectors.joining(", "));
        String valueOfField = fields.stream().sorted().map(s -> ":" + s)
                .collect(Collectors.joining(", "));
        return insert + insertField + ") values(" + valueOfField + ")";
    }

    public static String generateDeleteSql(String tableName) {
        return "delete from " + tableName + " where id=:id";
    }
}
