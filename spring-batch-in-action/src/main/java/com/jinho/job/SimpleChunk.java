package com.jinho.job;

import com.jinho.job.parameter.RequestDateParam;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SimpleChunk {

    public static final String JOB_NAME = "simpleChunkJob";
    public static final String BEAN_PREFIX = JOB_NAME + "_";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Qualifier(BEAN_PREFIX + "jobParameter")
    private final RequestDateParam requestDateParam;


    @Bean(BEAN_PREFIX + "jobParameter")
    @JobScope
    public RequestDateParam jobParameter() {
        return new RequestDateParam();
    }

    @Bean(JOB_NAME)
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
            .start(step())
            .incrementer(new RunIdIncrementer())
            .build();
    }

    @Bean(BEAN_PREFIX + "step")
    public Step step() {
        return stepBuilderFactory.get(BEAN_PREFIX + "step")
            .<String, String>chunk(10)
            .reader(getReader())
            .writer(items -> {
                for (final String item : items) {
                    log.info(">>> item = {}", item);
                }
            })
            .build();
    }

    @Bean(BEAN_PREFIX + "reader")
    @StepScope
    public ListItemReader<String> getReader() {
        final LocalDate requestDate = requestDateParam.getRequestDate();
        log.info(">>> date = {}", requestDate);
        return new ListItemReader<>(List.of("1", "2", "3"));
    }

}
