package com.jinho.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RetryTemplateConfig {

    public static final String JOB_NAME = "retryTemplateJob";
    public static final String BEAN_PREFIX = JOB_NAME + "_";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RetryTemplate retryTemplate;

    @Bean(JOB_NAME)
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
            .start(step1())
            .build();
    }

    @Bean(BEAN_PREFIX + "step")
    public Step step1() {
        return stepBuilderFactory.get(BEAN_PREFIX + "step")
            .<Integer, Integer>chunk(5)
            .reader(reader())
            .processor(processor())
            .writer(items -> log.info("writer = {}", items))
            .faultTolerant()
            .skip(IllegalArgumentException.class)
            .skipLimit(2)
            .build();
    }

    // 로그 마지막에 11이 찍히는것은 무시. 값을 추가로 읽는 것이 아닌 null을 리턴한다.
    private ItemReader<Integer> reader() {
        return new ItemReader<>() {
            int i = 0;
            @Override
            public Integer read() {
                i++;
                log.info(">>> reader = {}", i);
                return i > 10 ? null : i;
            }

        };
    }

    private ItemProcessor< Integer, Integer> processor(){
        return item -> {
            retryTemplate.execute(context -> {
                log.info(">>> process = {}", item);
                if (item == 5) {
                    throw new IllegalArgumentException();
                }
                return item;
            });
            return item;
        };
    }
}
