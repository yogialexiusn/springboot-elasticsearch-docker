package com.example.springbootelasticsearchfuzzyquery_dsl;

import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@ComponentScan(basePackages = "com.example")
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan("com.example")
public class SpringBootElasticSearchFuzzyQueryDslApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootElasticSearchFuzzyQueryDslApplication.class, args);
    }

}
