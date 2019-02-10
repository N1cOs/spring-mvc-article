package org.springframework.beans.factory;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class BeanFactory {

    private Map<String, Object> beans = new HashMap<>();

    public Object getBean(String beanName){
        return beans.get(beanName);
    }

    public <T> T getBean(Class<T> beanClass){
        Object requiredBean = null;
        for(Object bean: beans.values()){
            if(bean.getClass().equals(beanClass)){
                if(requiredBean == null)
                    requiredBean = bean;
                else
                    throw new IllegalArgumentException("More than one bean found with class " +
                            beanClass.getCanonicalName());
            }
        }
        return (T)requiredBean;
    }

    public BeanFactory(String basePackage){
        instantiate(basePackage);
        injectBeanFactory();
    }

    public Map<String, Object> getBeans() {
        return beans;
    }

    private void instantiate(String basePackage){
        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();

            String path = basePackage.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File file = new File(resource.toURI());

                for (File classFile : file.listFiles()) {
                    String fileName = classFile.getName();
                    if (fileName.endsWith(".class")) {
                        String className = fileName.substring(0, fileName.lastIndexOf("."));
                        Class<?> classObject = Class.forName(basePackage + "." + className);

                        if (classObject.isAnnotationPresent(Component.class) ||
                                classObject.isAnnotationPresent(Controller.class)) {
                            Object instance = classObject.newInstance();
                            String beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
                            beans.put(beanName, instance);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void injectBeanFactory(){
        for(Object bean : beans.values()){
            if(bean instanceof BeanFactoryAware){
                ((BeanFactoryAware)bean).setBeanFactory(this);
            }
        }
    }


}
