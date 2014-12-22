package com.coupang.c4.step14.beanfactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ScopedBeanFactory implements BeanFactory {
	private Map<Scope, BeanFactory> beanFactories = new HashMap<Scope, BeanFactory>();
    private Map<String, BeanDefinition> beans = new HashMap<String, BeanDefinition>();

	@Override
	public <T> T getInstance(Class<T> type) throws InvocationTargetException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        String classFullName = type.getCanonicalName();

        Scope scope = beans.get(classFullName).getScope();
        if(scope != null) {
            BeanFactory beanFactory = beanFactories.get(scope);
            return beanFactory.getInstance(type);
        }

        return null;
	}

	@Override
	public Object getInstance(String beanName) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        String classFullName = null;

        for (Entry<String, BeanDefinition> entry : beans.entrySet()) {
            BeanDefinition bean = entry.getValue();

            if (bean.getBeanName().equals(beanName)) {
                classFullName = bean.getClassFullName();
                break;
            }
        }

        if (classFullName == null) {
            throw new ClassNotFoundException();
        }

        Scope scope = beans.get(classFullName).getScope();
        if (scope != null) {
            BeanFactory beanFactory = beanFactories.get(scope);
            return beanFactory.getInstance(beanName);
        }

        return null;
    }

    private Object getInstanceFromClassFullName(String classFullName) {

//        Scope scope = beans.get(classFullName).getScope();
//        if(scope != null) {
//            BeanFactory beanFactory = beanFactories.get(scope);
//            return beanFactory.getInstance(type);
//        }
//
        return null;
    }


	@Override
	public void addNewBean(BeanDefinition beanDefinition) {

		if(!this.beanFactories.containsKey(beanDefinition.getScope())) {

            switch (beanDefinition.getScope()) {
                case PROTOTYPE:
                    this.beanFactories.put(beanDefinition.getScope(), new PrototypeBeanFactory());
                    break;

                case SINGLETON:
                default:
                    this.beanFactories.put(beanDefinition.getScope(), new SingletonBeanFactory());
                    break;
            }

		}

        this.beanFactories.get(beanDefinition.getScope()).addNewBean(beanDefinition);
        this.beans.put(beanDefinition.getClassFullName(), beanDefinition);
    }
}
