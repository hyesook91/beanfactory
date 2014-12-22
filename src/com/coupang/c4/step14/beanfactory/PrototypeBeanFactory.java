package com.coupang.c4.step14.beanfactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map.Entry;

public class PrototypeBeanFactory implements BeanFactory {

    private HashMap<String, BeanDefinition> beans = new HashMap<String, BeanDefinition>();

    @Override
    public <T> T getInstance(Class<T> type) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {

        String classFullName = type.getCanonicalName();

        Object instance = getInstanceFromClassFullName(classFullName);

        return (T) instance;
    }

    @Override
    public Object getInstance(String beanName) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {

        String classFullName = null;
        Object instance;

        for (Entry<String, BeanDefinition> entry : beans.entrySet()) {
            BeanDefinition bean = entry.getValue();

            if(bean.getBeanName().equals(beanName)) {
                classFullName = bean.getClassFullName();
                break;
            }
        }

        if(classFullName == null) {
            throw new ClassNotFoundException();
        }

        instance = getInstanceFromClassFullName(classFullName);

        return instance;
    }


    private Object getInstanceFromClassFullName(String classFullName) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Object instance;

        Class<?> clazz = Class.forName(classFullName);

        Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        instance = constructor.newInstance();

        return instance;
    }


    @Override
    public void addNewBean(BeanDefinition beanDefinition) {
        if (!this.beans.containsKey(beanDefinition.getClassFullName())) {
            this.beans.put(beanDefinition.getClassFullName(), beanDefinition);
        }
    }


}
