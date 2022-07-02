package com.woozooha.steno.api;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class StenoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    StenoController stenoController() {
        return new StenoController();
    }

}
