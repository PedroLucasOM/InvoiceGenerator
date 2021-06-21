package com.system.invoicegenerator.step;

import com.system.invoicegenerator.model.InvoiceCreditCard;
import com.system.invoicegenerator.model.Transaction;
import com.system.invoicegenerator.reader.InvoiceCreditCardReader;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoiceCreditCardStepConfig {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step invoiceCreditCardStep(
            ItemStreamReader<Transaction> transactionItemReader,
            ItemProcessor<InvoiceCreditCard, InvoiceCreditCard> loadClientDataProcessor,
            ItemWriter<InvoiceCreditCard> invoiceCreditCardItemWriter,
            TotalTransactionsFooterCallback listener) {
        return stepBuilderFactory
                .get("invoiceCreditCardStep")
                .<InvoiceCreditCard, InvoiceCreditCard>chunk(1)
                .reader(new InvoiceCreditCardReader(transactionItemReader))
                .processor(loadClientDataProcessor)
                .writer(invoiceCreditCardItemWriter)
                .listener(listener)
                .build();
    }
}
