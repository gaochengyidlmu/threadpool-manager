package com.gcy.baiji.server.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcy.baiji.common.enums.ThreadPoolStateEnum;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * Jackson 实现 JSON 字段类型处理器 参考 https://www.jianshu.com/p/109cfc44051e
 * https://blog.csdn.net/SeptDays/article/details/96357404
 * https://segmentfault.com/a/1190000022471687
 */
@Slf4j
// 如果有新的 JSON 类型需要添加，则在此处加上对应的类
@MappedTypes(
    value = {
        String[].class,
        Integer[].class,
        List.class,
        ThreadPoolStateEnum.class,
    })
@MappedJdbcTypes(JdbcType.VARCHAR) // 指定关联的Java类型列表，此处不用变更
public class JacksonTypeHandler<T> extends BaseTypeHandler<T> {

  private static final ObjectMapper objectMapper;
  private Class<T> type;

  static {
    objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public JacksonTypeHandler() {
  }

  public JacksonTypeHandler(Class<T> type) {
    if (log.isTraceEnabled()) {
      log.trace("JacksonTypeHandler(" + type + ")");
    }
    if (null == type) {
      throw new PersistenceException("Type argument cannot be null");
    }
    this.type = type;
  }

  private T parse(String json) {
    try {
      if (json == null || json.length() == 0) {
        return null;
      }
      return objectMapper.readValue(json, type);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String toJsonString(T obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return parse(rs.getString(columnName));
  }

  @Override
  public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return parse(rs.getString(columnIndex));
  }

  @Override
  public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return parse(cs.getString(columnIndex));
  }

  @Override
  public void setNonNullParameter(
      PreparedStatement ps, int columnIndex, T parameter, JdbcType jdbcType) throws SQLException {
    ps.setString(columnIndex, toJsonString(parameter));
  }
}
