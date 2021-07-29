package com.jinho.job;

import com.jinho.domain.Order;
import com.jinho.domain.Product;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ProductToOrderConvertStep {

    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Value("${chunkSize:1000}")
    private int chunkSize;

    @Bean
    public Step productToOrderConvert() {
        return stepBuilderFactory.get("productToOrderConvert")
            .<Product, Order>chunk(chunkSize)
            .reader(productToOrderConvertReader())
            .processor(productToOrderConvertProcess())
            .writer(productToOrderConvertWriter())
            .build();
    }

    @Bean
    public JpaCursorItemReader<Product> productToOrderConvertReader() {
        return new JpaCursorItemReaderBuilder<Product>()
            .name("productToOrderConvertReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString("select p from Product p")
            .build();
    }

    private ItemProcessor<Product, Order> productToOrderConvertProcess() {
        return product -> new Order(product.getAmount());
    }

    private ItemWriter<Order> productToOrderConvertWriter() {
        final JpaItemWriter<Order> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
