package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootWebprojectJpa1Application {

    public static void main(String[] args) {

        Hello hello = new Hello();
        hello.setDate("hello");

        String data = hello.getDate();

        System.out.println("data : " + data);

        SpringApplication.run(SpringbootWebprojectJpa1Application.class, args);

    }

}
