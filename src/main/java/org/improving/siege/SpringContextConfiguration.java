package org.improving.siege;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Random;
import java.util.Scanner;

@Configuration
@ComponentScan("org.improving.siege")
public class SpringContextConfiguration {

    @Bean
    public Scanner getScanner() { return new Scanner(System.in); }

    @Bean
    public Random getRandom() { return new Random(); }

}
