package com.system.invoicegenerator.writer;

import com.system.invoicegenerator.model.Transaction;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImportDataWriterClassifierConfig {

    @Bean
    public ClassifierCompositeItemWriter<Object> importDataClassifierWriter(
            @Qualifier("creditCardFileWriter") JdbcBatchItemWriter<Object> creditCardFileWriter,
            @Qualifier("transactionFileWriter") JdbcBatchItemWriter<Object> transactionFileWriter
    ) {
        return new ClassifierCompositeItemWriterBuilder<Object>()
                .classifier(classifier(creditCardFileWriter, transactionFileWriter))
                .build();
    }

    private Classifier<Object, ItemWriter<? super Object>> classifier(JdbcBatchItemWriter<Object> creditCardFileWriter, JdbcBatchItemWriter<Object> transactionFileWriter) {
        return new Classifier<Object, ItemWriter<? super Object>>() {
            @Override
            public ItemWriter<? super Object> classify(Object object) {
                if (object instanceof Transaction) {
                    return transactionFileWriter;
                }
                return creditCardFileWriter;
            }
        };
    }

}
