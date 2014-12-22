package com.coupang.c4.step14.beanfactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by coupang on 14. 12. 21..
 */
public interface BeanFactory extends BeanRegistry {
    <T> T getInstance(Class<T> type) throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException;
    Object getInstance(String beanName) throws IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException;
}
