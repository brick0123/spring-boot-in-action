package com.jinho.job;

import com.jinho.domain.Product;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
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

@Configuration
@RequiredArgsConstructor
public class UpdateProductAmountStep {

    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Value("${chunkSize:1000}")
    private int chunkSize;

    @Bean
    public Step updateProductAmount() {
        return stepBuilderFactory.get("updateProductAmount")
            .<Product, Product>chunk(chunkSize)
            .reader(updateProductAmountReader())
            .processor(updateProductAmountProcessor())
            .writer(updateProductAmountWriter())
            .build();
    }

    @Bean
    public JpaCursorItemReader<Product> updateProductAmountReader() {
        return new JpaCursorItemReaderBuilder<Product>()
            .name("updateProductAmountReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString("select p from Product p")
            .build();
    }

    private ItemProcessor<? super Product, ? extends Product> updateProductAmountProcessor() {
        return product -> {
            product.increaseAmount();
            return product;
        };
    }

    private ItemWriter<Product> updateProductAmountWriter() {
        final JpaItemWriter<Product> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
