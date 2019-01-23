package dao;

import java.lang.reflect.Method;

import bll.PC_2;

public class FS {
	public static void test1() throws Exception{
		Class<?> c=PC_2.class;//反射另外一个类Jdbc,获得实体类
		Object obj= c.newInstance();//获取类的实例对象
		Method method =Class.forName("bll.PC_2")
				.getMethod("test2",null);//装载方法
		method.invoke(obj,null);//调用参数为null的方法
	} 
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		test1();
	}

}
