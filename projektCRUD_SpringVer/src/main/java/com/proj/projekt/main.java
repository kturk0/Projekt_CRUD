package com.proj.projekt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class main {
    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(main.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
        Login logi = new Login();
        logi.setVisible(true);
    }
}
