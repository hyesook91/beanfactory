package com.coupang.c4.step14;

import com.coupang.c4.step14.beanfactory.ScopedBeanFactory;
import com.coupang.c4.step14.beanfactory.SimpleBeanFactory;
import com.coupang.c4.step14.beans.Sample1;
import com.coupang.c4.step14.beans.Sample2;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


public class Main {
	public static void main(String[] args) {
        String configurationPath = "/com/coupang/c4/step14/";
        SimpleBeanFactory simpleBeanFactory = null;
        try {
            simpleBeanFactory = SimpleBeanFactory.createScopedBeanFactory(
                    configurationPath + "bean-definitions.properties");

            Sample1 instance = simpleBeanFactory.getInstance(Sample1.class);
            if(instance != null) {
                System.out.println("Sample1 instance: " + instance);

                instance = simpleBeanFactory.getInstance(Sample1.class);
                System.out.println("Sample1 instance: " + instance);
            }
            else {
                System.out.println("Sample1 is null ");
            }

            Object instance2 = simpleBeanFactory.getInstance("sample2");

            if(instance2 != null) {
                System.out.println("Sample2 instance: " + instance2);

                instance2 = simpleBeanFactory.getInstance("sample2");
                System.out.println("Sample2 instance: " + instance2);

                System.out.println("Sample2 is assignable: " + Sample2.class.isAssignableFrom(instance2.getClass()));
                System.out.println("instance2 instance of Sample2: " + (instance2 instanceof Sample2));
            }
            else {
                System.out.println("Sample2 is null ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
