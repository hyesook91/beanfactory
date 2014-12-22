package com.coupang.c4.step14.beanfactory;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleBeanFactory implements BeanFactory{

    private List<BeanDefinitionLoader> beanDefinitionLoaders = new ArrayList<BeanDefinitionLoader>();
    private BeanFactory beanFactory;

    public static SimpleBeanFactory createScopedBeanFactory(String propertyPath, String... otherPropertyPathes) throws IOException {
        return new SimpleBeanFactory(new ScopedBeanFactory(), propertyPath, otherPropertyPathes);
    }

    public SimpleBeanFactory(BeanFactory beanFactory, String propertyPath, String... otherPropertyPathes) throws IOException {
        this.beanFactory = beanFactory;
        // 필수로 하나는 있어야 해서 파라미터 분리.
        this.beanDefinitionLoaders.add(findBeanDefinitionLoader(propertyPath));
        fillOtherPropertyLoaders(otherPropertyPathes);

        for (BeanDefinitionLoader loader : this.beanDefinitionLoaders) {
            BeanDefinition[] loadBeans = loader.loadBeans();
            for (BeanDefinition beanDefinition : loadBeans) {
                this.addNewBean(beanDefinition);
            }
        }
    }

    private void fillOtherPropertyLoaders(String... otherPropertyPathes) {
        if (otherPropertyPathes == null) {
            return;
        }

        for (String other : otherPropertyPathes) {
            this.beanDefinitionLoaders.add(findBeanDefinitionLoader(other));
        }
    }

    private BeanDefinitionLoader findBeanDefinitionLoader(String propertyPath) {

        return new PropertyFileBeanDefinitionLoader(propertyPath);
    }

    @Override
    public <T> T getInstance(Class<T> type) throws InvocationTargetException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return this.beanFactory.getInstance(type);
    }

    @Override
    public Object getInstance(String beanName) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        return this.beanFactory.getInstance(beanName);
    }

    @Override
    public void addNewBean(BeanDefinition beanDefinition) {
        this.beanFactory.addNewBean(beanDefinition);
    }
}
