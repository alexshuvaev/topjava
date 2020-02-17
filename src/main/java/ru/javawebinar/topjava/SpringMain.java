package ru.javawebinar.topjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.util.Arrays;

public class SpringMain {
    protected static final Logger log = LoggerFactory.getLogger(SpringMain.class);

    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
           MealRestController controller = appCtx.getBean(MealRestController.class);
            //SecurityUtil.setAuthUserId(5);
            //controller.get(2);
            //controller.delete(2);
        }
    }
}
