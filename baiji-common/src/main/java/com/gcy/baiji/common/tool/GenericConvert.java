package com.gcy.baiji.common.tool;

public class GenericConvert {

  public static <I, O> O convert(I input, Class<O> clazz) {
    O result = ReflectionConstructors.newInstance(clazz);
    ObjectUtils.merge(input, result);
    return result;
  }
}
