package com.gcy.baiji.common.utils;

import com.gcy.baiji.tools.reflection.ReflectionConstructors;
import com.gcy.baiji.tools.reflection.ReflectionFields;
import com.gcy.baiji.tools.reflection.ReflectionMethods;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class ObjectUtils {

  // 将 from 中的数据合并到 to 中
  public static <T, F> T merge(F from, T to) {
    T result = (T) ReflectionConstructors.newInstance(to.getClass());

    Field[] fields = ReflectionFields.getFields(to.getClass());
    // 遍历 to 中的所有字段，然后调用 getter setter 进行插入
    for (Field field : fields) {
      Method toGetMethod = ReflectionMethods.getGetter(to.getClass(), field);
      Method fromGetMethod = ReflectionMethods.getGetter(from.getClass(), field);
      Method setMethod = ReflectionMethods.setGetter(to.getClass(), field);
      if ((toGetMethod != null || fromGetMethod != null) && setMethod != null) {
        Object value;

        if (toGetMethod != null && (value = ReflectionMethods.invoke(toGetMethod, to)) != null) {
          ReflectionMethods.invoke(setMethod, result, value);
        }
        if (fromGetMethod != null
            && (value = ReflectionMethods.invoke(fromGetMethod, from)) != null) {
          ReflectionMethods.invoke(setMethod, result, value);
        }
      }
    }
    return result;
  }


  public static <T> boolean daoCompareTo(T o1, T o2) {
    return compareTo(o1, o2, List.of("createdAt", "updatedAt"));
  }

  public static <T> boolean compareTo(T o1, T o2, List<String> excludeFields) {
    if (o1 == null || o2 == null) {
      return false;
    }

    Field[] fields = ReflectionFields.getFields(o1.getClass());
    for (Field field : fields) {
      if (excludeFields != null && excludeFields.contains(field.getName())) {
        continue;
      }
      Method getMethod = ReflectionMethods.getGetter(o1.getClass(), field);
      Object v1 = ReflectionMethods.invoke(getMethod, o1);
      Object v2 = ReflectionMethods.invoke(getMethod, o2);
      if (v1 == null && v2 == null) {
        continue;
      }

      if (v1 == null || !v1.equals(v2)) {
        return false;
      }
    }
    return true;
  }
}
