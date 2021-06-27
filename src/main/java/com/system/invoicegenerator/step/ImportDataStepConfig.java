package com.system.invoicegenerator.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImportDataStepConfig {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step importDataStep(
            FlatFileItemReader<Object> importDataReader,
            ClassifierCompositeItemWriter<Object> importDataClassifierWriter
    ) {
        return stepBuilderFactory
                .get("importDataStep")
                .chunk(1000)
                .reader(importDataReader)
                .writer(importDataClassifierWriter)
                .build();
    }
}
