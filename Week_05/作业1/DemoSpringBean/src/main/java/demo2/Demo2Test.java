package demo2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo2Test {
    public static void main(String args[]) {
        ApplicationContext context = new ClassPathXmlApplicationContext("annotationbean.xml");
        User2 u = context.getBean("u2", User2.class);
        System.out.println(u);
    }
}
