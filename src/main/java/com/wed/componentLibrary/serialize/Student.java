package com.wed.componentLibrary.serialize;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 
 * Student.java
 * @author wed
 * @date 2016年9月9日  下午2:52:57
 * @description
 * 说明：
 * 	1、transient 关键字，不序列化某些字段，和Serializable一起使用
 * 	2、控制序列化字段，甚至该字段是被transient修饰的字段也能将其序列化。手动序列化需要添加两个私有(private)方法(writeObject()和readObject())，在该私有方法中控制序列化字段。
 *  3、扩展知识：Externalizable  
 */
public class Student implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2711252660146260659L;
	private Integer id;
	private String name;
	private Integer age;
	private String hobby;
	private transient String password;	
	
	public Student(){
		
	}
	public Student(String name,Integer age,String hobby,String password){
		this.name = name;
		this.age = age;
		this.hobby = hobby;
		this.password = password;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "name:"+this.name+",age:"+this.age+",hobby:"+this.hobby+",password:"+this.password;
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.defaultWriteObject();	//序列化所有非transient字段,必须是该方法的第一个操作 
		out.writeObject(password);	//序列化transient字段 
	}
	
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException{
		in.defaultReadObject();		//反序列化所有非transient字段,必须是该方法的第一个操作 
		password = (String)in.readObject();	//反序列化transient字段 
	}
}
