package org.summarly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
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

    @Bean
    CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
