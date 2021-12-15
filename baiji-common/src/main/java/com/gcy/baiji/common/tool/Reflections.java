package com.gcy.baiji.common.tool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflections {

  public static Field[] getFields(Class<?> clazz) {
    return clazz.getDeclaredFields();
  }

  public static Field getField(Class<?> clazz, String fieldName) {
    try {
      return clazz.getDeclaredField(fieldName);
    } catch (NoSuchFieldException e) {
      if (clazz.getSuperclass() != null) {
        return getField(clazz.getSuperclass(), fieldName);
      }
      return null;
    }
  }


  public static Method[] getMethods(Class<?> clazz) {
    return clazz.getDeclaredMethods();
  }

  public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
    Method declaredMethod = null;
    try {
      declaredMethod = clazz.getDeclaredMethod(methodName, parameterTypes);
    } catch (NoSuchMethodException ignored) {
    }
    return declaredMethod;
  }

  public static Method findMethod(Class<?> clazz, String methodName) {
    Method[] methods = getMethods(clazz);
    for (Method method : methods) {
      if (method.getName().equals(methodName)) {
        return method;
      }
    }
    return null;
  }

  public static boolean compareMethod(Method m1, Method m2) {
    if (m1 == null || m2 == null) {
      return false;
    }
    if (!m1.getName().equals(m2.getName())) {
      return false;
    }
    if (m1.getParameterCount() != m2.getParameterCount()) {
      return false;
    }
    for (int i = 0; i < m1.getParameterCount(); i++) {
      if (!m1.getParameters()[i].equals(m2.getParameters()[i])) {
        return false;
      }
    }
    return true;
  }
}
