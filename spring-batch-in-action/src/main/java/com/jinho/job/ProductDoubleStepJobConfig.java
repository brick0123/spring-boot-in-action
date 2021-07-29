package com.jinho.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ProductDoubleStepJobConfig {

    private static final String JOB_NAME = "productIntegrationJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final UpdateProductAmountStep updateProductAmountStep;
    private final ProductToOrderConvertStep productToOrderConvertStep;

    @Bean(JOB_NAME)
    public Job productIntegrationJobConfig() {
        return jobBuilderFactory.get(JOB_NAME)
            .preventRestart()
            .start(updateProductAmountStep.updateProductAmount())
            .next(productToOrderConvertStep.productToOrderConvert())
            .build();
    }

}
