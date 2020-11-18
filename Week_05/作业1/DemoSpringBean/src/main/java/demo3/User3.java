package demo3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "u3")
public class User3 {
    @Value(value = "WangWu")
    private String name;
    @Value(value = "24")
    private int age;

    @Override
    public String toString() {
        return "user{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
