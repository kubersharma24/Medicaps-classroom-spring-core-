package com.kuber.medicapclassrooms.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class NewContext {
    private static ApplicationContext context;
    public static ApplicationContext getContext(){
        if(context==null){
            return new ClassPathXmlApplicationContext("applicationContext.xml");
        }
        return context;
    }
}
