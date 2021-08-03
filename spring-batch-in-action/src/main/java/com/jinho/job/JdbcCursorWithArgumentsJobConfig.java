package com.jinho.job;

import com.jinho.domain.Product;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JdbcCursorWithArgumentsJobConfig {

    private static final String JOB_NAME = "jdbcCursorWithArguments";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Value("${chunkSize:1000}")
    private int chunkSize;

    @Bean(JOB_NAME)
    public Job jdbcCursorWithArgumentsJob() {
        return jobBuilderFactory.get(JOB_NAME)
            .start(jdbcCursorWithArgumentsStep())
            .build();
    }

    @Bean
    public Step jdbcCursorWithArgumentsStep() {
        return stepBuilderFactory.get("jdbcCursorWithArgumentsStep")
            .<Product, Product>chunk(chunkSize)
            .reader(jdbcCursorWithArgumentsReader(null, null))
            .writer(jdbcCursorWithArgumentsWriter())
            .build();
    }

    @Bean
    @StepScope
    public JdbcCursorItemReader<Product> jdbcCursorWithArgumentsReader(
        @Value("#{jobParameters[name]}") String name,
        @Value("#{jobParameters[amount]}") Integer amount
    ) {
        log.info(">>> name = {}, amount = {}", name, amount);
        String sql = "select p.product_id, p.amount, p.name from Product p where p.name = ? and p.amount = ?";

//        final Map<String, Object> parameters = new HashMap<>();
//        parameters.put("1", name);
//        parameters.put("2", amount);

        final List<Object> parameters = new ArrayList<>();
        parameters.add(name);
        parameters.add(amount);

        return new JdbcCursorItemReaderBuilder<Product>()
            .name("jdbcCursorWithArgumentsReader")
            .dataSource(dataSource)
            .rowMapper(new BeanPropertyRowMapper<>(Product.class))
            .sql(sql)
            .queryArguments(parameters)
            .build();
    }


    private ItemWriter<? super Product> jdbcCursorWithArgumentsWriter() {
        return list -> {
            for (Product product : list) {
                log.info(">>> product = {}", product);
            }
        };
    }

}
