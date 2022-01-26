package com.budgety.api;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class BudgetyApplication {
    private static final Logger logger = LogManager.getLogger(BudgetyApplication.class);
    @Bean
    public ModelMapper modelMapper(){return new ModelMapper();}
    public static void main(String[] args) {
        SpringApplication.run(BudgetyApplication.class, args);
    }
}
