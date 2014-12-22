package com.coupang.c4.step14.beanfactory;

import java.io.IOException;

/**
 * Created by coupang on 14. 12. 21..
 */
public interface BeanDefinitionLoader {
    public BeanDefinition[] loadBeans() throws IOException;
}
