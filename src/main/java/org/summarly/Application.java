package org.summarly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.summarly.lib.LexRankSummarizationService;
import org.summarly.lib.SummarizationService;

@Configuration
@ComponentScan(value = "org.summarly")
@EnableAutoConfiguration
public class Application {

    @Bean
    SummarizationService summarizationService() {
        return new LexRankSummarizationService();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
