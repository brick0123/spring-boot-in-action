package com.jinho.job;

import com.jinho.job.parameter.RequestDateJobParameter;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.support.ListItemReader;
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

    private final RequestDateJobParameter requestDateJobParameter;

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
            .<Long, Long>chunk(1)
            .reader(getReader())
            .writer(items -> log.info(">>> item = {}", items))
            .build();
    }

    @Bean(BEAN_PREFIX + "reader")
    @StepScope
    public ListItemReader<Long> getReader() {
        final LocalDate requestDate = requestDateJobParameter.getRequestDate();
        log.info(">>> requestDate = {}", requestDate);
        return new ListItemReader<>(List.of(1L, 2L, 3L));
    }
}
