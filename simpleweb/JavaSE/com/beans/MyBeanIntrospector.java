package com.beans;

import java.beans.BeanInfo;

import java.beans.EventSetDescriptor;

import java.beans.Introspector;

import java.beans.MethodDescriptor;

import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

/**
 * BeanInfo学习
 * @author slieer
 */
public class MyBeanIntrospector {

	public static void main(String[] args){
		new MyBeanIntrospector();
	}

	public MyBeanIntrospector() {
		try {
			// 实例化一个Bean
			MyBean beanObj = new MyBean();
			// 依据Bean产生一个相关的BeanInfo类
			BeanInfo bInfoObject = Introspector.getBeanInfo(beanObj.getClass(),
					beanObj.getClass().getSuperclass());

			// 定义一个用于显示的字符串
			String output = "";
			// 开始自省
			/*
			 * BeanInfo.getMethodDescriptors()
			 * 用于获取该Bean中的所有允许公开的成员方法，以MethodDescriptor数组的形式返回
			 * 
			 * MethodDescriptor 类用于记载一个成员方法的所有信息
			 * MethodDescriptor.getName() 获得该方法的方法名字
			 * MethodDescriptor.getMethod() 获得该方法的方法对象（Method类）
			 * Method类 记载一个具体的的方法的所有信息
			 * Method.getParameterTypes() 获得该方法所用到的所有参数，以Class数组的形式返回
			 * Class..getName() 获得该类型的名字
			 */

			output = "内省成员方法：\n";
			MethodDescriptor[] mDescArray = bInfoObject.getMethodDescriptors();
			for (int i = 0; i < mDescArray.length; i++){
				// 获得一个成员方法描述器所代表的方法的名字
				String methodName = mDescArray[i].getName();
				String methodParams = new String();

				// 获得该方法对象
				Method methodObj = mDescArray[i].getMethod();

				//通过方法对象获得该方法的所有参数，以Class数组的形式返回
				Class[] parameters = methodObj.getParameterTypes();
				if (parameters.length > 0){
					// 获得参数的类型的名字
					methodParams = parameters[0].getName();
					for (int j = 1; j < parameters.length; j++){
						methodParams = methodParams + ","
								+ parameters[j].getName();
					}
				}
				output += methodName + "(" + methodParams + ")\n";
			}
			System.out.println(output);

			/*
			 * BeanInfo.getPropertyDescriptors() 用于获取该Bean中的所有允许公开的成员属性，以PropertyDescriptor数组的形式返回
			 * PropertyDescriptor类  用于描述一个成员属性
			 * PropertyDescriptor.getName() 获得该属性的名字
			 * PropertyDescriptor.getPropertyType() 获得该属性的数据类型，以Class的形式给出
			 */

			output = "内省成员属性：\n";
			PropertyDescriptor[] mPropertyArray = bInfoObject
					.getPropertyDescriptors();

			for (int i = 0; i < mPropertyArray.length; i++){
				String propertyName = mPropertyArray[i].getName();

				Class propertyType = mPropertyArray[i].getPropertyType();

				output += propertyName + " ( " + propertyType.getName()
						+ " )\n";

			}

			System.out.println(output);
			/*
			 * BeanInfo.getEventSetDescriptors()
			 * 用于获取该Bean中的所有允许公开的成员事件，以EventSetDescriptor数组的形式返回
			 * 
			 * EventSetDescriptor类  用于描述一个成员事件
			 * EventSetDescriptor.getName()  获得该事件的名字
			 * EventSetDescriptor.getListenerType() 获得该事件所依赖的事件监听器，以Class的形式给出
			 */
			output = "内省绑定事件：\n";

			EventSetDescriptor[] mEventArray = bInfoObject
					.getEventSetDescriptors();

			for (int i = 0; i < mEventArray.length; i++){
				String EventName = mEventArray[i].getName();
				Class listenerType = mEventArray[i].getListenerType();
				output += EventName + "(" + listenerType.getName() + ")\n";

			}
			System.out.println(output);
			System.out.println("write by esonghui :");
		}
		catch (Exception e){
			System.out.println("异常：" + e);

		}
	}
}