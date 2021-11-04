package org.softwarevax.framework.mybatis.jdbc;

import java.io.Closeable;
import java.util.List;
import java.util.Map;

public interface JdbcTemplate extends Closeable {

    List<Map<String, Object>> statement(String sql);
}
