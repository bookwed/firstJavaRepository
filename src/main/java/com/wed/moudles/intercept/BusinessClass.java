package com.wed.moudles.intercept;

public class BusinessClass implements BusinessInterface {

	@Override
	public void doSomething() {
		System.out.println("业务组件BusinessClass方法调用:doSomething()");
	}

}
