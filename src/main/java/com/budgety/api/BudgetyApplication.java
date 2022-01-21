package com.budgety.api;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BudgetyApplication {
    @Bean
    public ModelMapper modelMapper(){return new ModelMapper();}
    public static void main(String[] args) {
        SpringApplication.run(BudgetyApplication.class, args);
    }
}
