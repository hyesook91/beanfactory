package com.coupang.c4.step14.beanfactory;

/**
 * Created by coupang on 14. 12. 21..
 */
public class BeanDefinition {
    private String beanName;
    private String classFullName;
    private Scope scope = Scope.SINGLETON;

    public BeanDefinition(String beanName, String classFullName, Scope scope) {
        this.classFullName = classFullName;
        this.beanName = beanName;
        this.scope = scope;
    }


    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getClassFullName() {
        return classFullName;
    }

    public void setClassFullName(String classFullName) {
        this.classFullName = classFullName;
    }
}
