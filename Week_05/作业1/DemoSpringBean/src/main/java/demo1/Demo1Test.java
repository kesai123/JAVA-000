package demo1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo1Test {
    public static void main(String args[]) {
        ApplicationContext context = new ClassPathXmlApplicationContext("xmlbean.xml");
        User1 u = context.getBean("u1", User1.class);
        System.out.println(u);
    }
}
