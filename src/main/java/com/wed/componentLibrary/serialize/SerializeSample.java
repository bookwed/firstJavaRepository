package com.wed.componentLibrary.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

/**
 * java对象的序列化
 * @author wed
 * @date 2016年9月9日  下午1:52:09
 * @description
 * 说明：
 * 无论何种类型的数据，最终都要转换成二进制流在网络上进行传输。
 * 对象转二进制流的过程称为“对象的序列化”。
 * 二进制流转对象的过程称为“对象的反序列化”。
 * 比较成熟的方案：
 * 		1、Google的Protocal Buffers（性能优异、支持跨平台、编码侵入式强，需要编写proto文件）
 * 		2、Hessian（相对于PB效率稍低，比内置要高，对编程语言有良好的支持），二进制协议，适合发送二进制数据
 * 		3、java内置的序列化方式（无需第三方包，使用简单，效率偏低）
 * 		4、JSON
 * 		5、XMl
 * 
 */
public class SerializeSample {

	public static void main(String[] args) {
		
		Student zhangsan = new Student("张三",15,"打球","123456");
//		serializeByJava(zhangsan);
		
		serializeByHessian(zhangsan);
	
	}
	/**
	 * serialize by java
	 */
	public static void serializeByJava(Student zhangsan){
		try {
			/*//定义一个字节数组输出流(存储到字节数组) 
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			//对象输出流
			ObjectOutputStream out = new ObjectOutputStream(os);
			//将对象写入到字节数组输出，进行序列化
			out.writeObject(zhangsan);
			out.close();
			byte[] zhangsanByte = os.toByteArray();
			
			//字节数组输入流
			ByteArrayInputStream is = new ByteArrayInputStream(zhangsanByte);
			ObjectInputStream in = new ObjectInputStream(is);
			Student student = (Student)in.readObject();
			System.out.println(student.toString());*/
			
			
			//序列化一个对象(存储到一个文件) 
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:/person.out")); 
			oos.writeObject(zhangsan); 
			oos.close(); 
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("person.out")); 
			Student p = (Student)ois.readObject(); 
			System.out.println(p); 
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * serialize by Hessian
	 */
	public static void serializeByHessian(Student zhangsan){
		try {
			//序列化为字节数组
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			HessianOutput ho = new HessianOutput(os);
			ho.writeObject(zhangsan);
			byte[] zhangsanByte = os.toByteArray();
			
			//反序列化还原为对象
			ByteArrayInputStream is = new ByteArrayInputStream(zhangsanByte);
			HessianInput hi = new HessianInput(is);
			Student student = (Student)hi.readObject();
			System.out.println(student.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
