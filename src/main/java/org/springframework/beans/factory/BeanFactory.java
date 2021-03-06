package org.springframework.beans.factory;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class BeanFactory {

    private ClassLoader classLoader;

    private Map<String, Object> beans = new HashMap<>();

    public BeanFactory(ClassLoader classLoader){
        this.classLoader = classLoader;
    }

    public Object getBean(String beanName){
        return beans.get(beanName);
    }

    public Map<String, Object> getBeans() {
        return beans;
    }

    public void addBean(String name, Object bean){
        beans.put(name, bean);
    }

    public void instantiate(String basePackage){
        try {
            String path = basePackage.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File file = new File(resource.toURI());
                parseFile(file, basePackage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseFile(File baseFile, String basePackage) throws Exception {
        for(File file : baseFile.listFiles()){
            String fileName = file.getName();
            if(file.isDirectory()){
                String newBasePackage = basePackage + "." + fileName;
                parseFile(file, newBasePackage);
            }
            else if(fileName.endsWith(".class")){
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                Class<?> classObject = Class.forName(basePackage + "." + className);
                parseClass(classObject);
            }
        }
    }

    private void parseClass(Class<?> classObject) throws IllegalAccessException, InstantiationException {
        if (classObject.isAnnotationPresent(Component.class) ||
                classObject.isAnnotationPresent(RestController.class)) {
            Object instance = classObject.newInstance();
            String className = classObject.getName();
            String beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
            beans.put(beanName, instance);
        }
    }
}
