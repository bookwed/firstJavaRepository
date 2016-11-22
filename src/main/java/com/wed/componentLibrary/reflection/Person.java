package com.wed.componentLibrary.reflection;

public class Person {

	private String name;
	private int age;
	
	public Person(String name,int age){
		this.name = name;
		this.age = age;
	}
	
	private String getInfo(String str,int num){
		return str + num + "apples";
	}
}
