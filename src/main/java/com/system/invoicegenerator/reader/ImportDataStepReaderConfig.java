package com.system.invoicegenerator.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ImportDataStepReaderConfig {

    @StepScope
    @Bean
    public FlatFileItemReader<Object> importDataReader(
            @Value("file:${spring-batch-learning.input-folder}data.txt") Resource resource,
            LineMapper<Object> importDataLineMapper
    ) {
        return new FlatFileItemReaderBuilder<>()
                .name("importDataReader")
                .resource(resource)
                .lineMapper(importDataLineMapper)
                .build();
    }

}
