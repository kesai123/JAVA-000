package demo2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "u2")
public class User2 {
    @Value(value = "LiSi")
    private String name;
    @Value(value = "23")
    private int age;

    @Override
    public String toString() {
        return "user{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}