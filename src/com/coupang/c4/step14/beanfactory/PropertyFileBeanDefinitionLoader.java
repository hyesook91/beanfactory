package com.coupang.c4.step14.beanfactory;

import com.coupang.c4.ResourceUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by coupang on 14. 12. 21..
 */
public class PropertyFileBeanDefinitionLoader extends AbstractBeanDefinitionLoader{

    protected PropertyFileBeanDefinitionLoader(String configurationPath) {
        super(configurationPath);
    }

    @Override
    BeanDefinition[] loadBeans(InputStream inputStream) throws IOException {
        String[] lines = ResourceUtil.readFully(inputStream);
        List<BeanDefinition> result = new ArrayList<BeanDefinition>();
        if(lines != null){
            for(String line : lines){
                String[] parsed = line.split("=");
                String[] parsed2 = parsed[1].split(":");
                Scope scope = Scope.valueOf(parsed2[0]);

                result.add(new BeanDefinition(parsed[0], parsed2[1], scope));
            }
        }
        return result.toArray(new BeanDefinition[0]);
    }
}
