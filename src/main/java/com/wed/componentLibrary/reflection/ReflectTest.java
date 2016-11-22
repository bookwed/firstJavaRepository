package com.wed.componentLibrary.reflection;

import java.lang.reflect.InvocationTargetException;

public class ReflectTest {

	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Person person = new Person("jack",15);
		
		System.out.println("jack's name:"+ReflectionUtil.getValue(person, "name"));
		System.out.println("jack's age:"+ReflectionUtil.getValue(person, "age"));
		
		ReflectionUtil.setValue(person, "name", "jobs");
		ReflectionUtil.setValue(person, "age", 50);
		System.out.println("jack's name:"+ReflectionUtil.getValue(person, "name"));
		System.out.println("jack's age:"+ReflectionUtil.getValue(person, "age"));
		
		String result = (String)ReflectionUtil.callMethod(person, "getInfo", new Class[]{String.class,int.class}, new Object[]{"I have ",4 });
		System.out.println("result:"+result);
		
	}
}
