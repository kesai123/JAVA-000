package demo3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Demo3Test {
    public static void main(String args[]) {
        ApplicationContext context = new AnnotationConfigApplicationContext(User3Config.class);
        User3 u = context.getBean("u3", User3.class);
        System.out.println(u);
    }
}
