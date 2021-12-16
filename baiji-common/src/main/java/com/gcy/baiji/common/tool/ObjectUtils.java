package com.gcy.baiji.common.tool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ObjectUtils {

  // 将 from 中的数据合并到 to 中
  public static <T, F> void merge(F from, T to) {
    Field[] fields = ReflectionFields.getFields(to.getClass());
    // 遍历 to 中的所有字段，然后调用 getter setter 进行插入
    for (Field field : fields) {
      Method getMethod = ReflectionMethods
          .getMethod(from.getClass(), ReflectionMethods.buildGetMethodName(field.getName()));
      Method setMethod = ReflectionMethods
          .findMethodByName(to.getClass(), ReflectionMethods.buildSetMethodName(field.getName()));
      if (getMethod != null && setMethod != null) {
        Object value;
        if ((value = ReflectionMethods.invoke(getMethod, from)) != null) {
          ReflectionMethods.invoke(setMethod, to, value);
        }
      }
    }
  }
}
