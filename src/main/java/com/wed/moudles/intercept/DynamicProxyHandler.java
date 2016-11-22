package com.wed.moudles.intercept;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class DynamicProxyHandler implements InvocationHandler {

	private Object business;	//被代理对象
	
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return null;
	}
}
