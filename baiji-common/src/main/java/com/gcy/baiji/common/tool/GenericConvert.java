package com.gcy.baiji.common.tool;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class GenericConvert {

  public static <I, O> O convert(I input, Class<O> clazz) {
    O result = null;
    try {
      Constructor<?> constructor = clazz.getConstructor();
      result = (O) constructor.newInstance();

      Field[] fields = Reflections.getFields(clazz);
      for (Field field : fields) {
        String fieldName = field.getName();
        String getMethodName =
            "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        String setMethodName =
            "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method getMethod = Reflections.getMethod(input.getClass(), getMethodName);
        Method setMethod = Reflections.findMethod(clazz, setMethodName);
        if (getMethod == null || setMethod == null) {
          continue;
        }
        setMethod.invoke(result, getMethod.invoke(input));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
}
