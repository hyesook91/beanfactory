package com.coupang.c4.step14.beanfactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SingletonBeanFactory implements BeanFactory {
    private HashMap<String, Object> instances = new HashMap<String, Object>();
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

        Object instance = instances.get(classFullName);

        if(instance != null) {
            return instance;
        }

        Class<?> clazz = Class.forName(classFullName);

        Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
        instance = constructor.newInstance();

        instances.put(classFullName, instance);

        return instance;
    }

    @Override
    public void addNewBean(BeanDefinition beanDefinition) {
        if (!this.beans.containsKey(beanDefinition.getClassFullName())) {
            this.beans.put(beanDefinition.getClassFullName(), beanDefinition);
        }
    }
}
