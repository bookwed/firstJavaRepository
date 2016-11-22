package com.wed.componentLibrary.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtil {

	public static Object getValue(Object instance,String fieldName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Field field = instance.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(instance);
	}
	
	public static void setValue(Object instance,String fileName,Object value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Field field = instance.getClass().getDeclaredField(fileName);
		field.setAccessible(true);
		field.set(instance, value);
	}
	
	public static Object callMethod(Object instance,String methodName,Class[] classes,Object[] objects) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Method method = instance.getClass().getDeclaredMethod(methodName, classes);
		method.setAccessible(true);
		return method.invoke(instance, objects);
	}
	
	
	
}
