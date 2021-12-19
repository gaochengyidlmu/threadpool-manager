package com.gcy.baiji.common.utils;

import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class ObjectUtilsTests {

  @Test
  public void testMerge() {

  }

  @Test
  public void testCompareDao() {
    Date date = new Date();

    A a1 = new A();
    a1.setX("heihei");
    a1.setY(1);
    a1.setZ(date);

    A a2 = new A();
    a2.setX("heihei");
    a2.setY(1);
    a2.setZ(date);

    Assert.assertTrue(ObjectUtils.compareTo(a1, a2, null));

    A a3 = new A();
    a3.setX("heiheih");
    a3.setY(1);
    a3.setZ(date);
    Assert.assertFalse(ObjectUtils.compareTo(a1, a3, null));

    A a4 = new A();
    a4.setX("heihei");
    a4.setY(1);
    a4.setZ(new Date());
    Assert.assertFalse(ObjectUtils.compareTo(a1, a4, null));

    A a5 = new A();
    a5.setX("heihei");
    a5.setY(1);
    a5.setZ(new Date());
    Assert.assertTrue(ObjectUtils.compareTo(a1, a5, List.of("z")));

    A a6 = new A();
    a6.setX(null);
    a6.setY(1);
    a6.setZ(date);

    Assert.assertFalse(ObjectUtils.compareTo(a6, a1, null));
  }

  public static class A {

    private String x;
    private int y;
    private Date z;

    public String getX() {
      return x;
    }

    public void setX(String x) {
      this.x = x;
    }

    public int getY() {
      return y;
    }

    public void setY(int y) {
      this.y = y;
    }

    public Date getZ() {
      return z;
    }

    public void setZ(Date z) {
      this.z = z;
    }
  }
}
