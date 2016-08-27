package com.example.chenbing.animdvedemo.Utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Project Name:AnimDveDemo
 * Author:IceChen
 * Date:16/8/23
 * Notes:
 */
public class ReflectUtils {

  public static Method getMethod(@NonNull Class<?> clazz, @NonNull String methodName) throws NoSuchMethodException {
    Method method = clazz.getDeclaredMethod(methodName);
    method.setAccessible(true);
    return method;
  }

  public static void invokeMethod(@NonNull Class<?> clazz, @NonNull String methodName) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
    getMethod(clazz, methodName).invoke(clazz.newInstance());
  }

  public static Field getVariable(@NonNull Class<?> clazz, @NonNull String variableName) throws NoSuchFieldException {
    Field variable = clazz.getDeclaredField(variableName);
    variable.setAccessible(true);
    return variable;
  }
}
