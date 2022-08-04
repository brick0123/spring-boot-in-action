package com.jinho.job.parameter;

import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class JobParameterConfig {

    @Bean
    @JobScope
    public RequestDateJobParameter requestDateParam() {
        return new RequestDateJobParameter();
    }

    @Bean
    @JobScope
    public RequestDatetimeJobParameter requestDatetimeParam() {
        return new RequestDatetimeJobParameter();
    }
}
