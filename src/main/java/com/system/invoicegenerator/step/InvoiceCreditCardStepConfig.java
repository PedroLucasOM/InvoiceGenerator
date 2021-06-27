package com.system.invoicegenerator.step;

import com.system.invoicegenerator.model.InvoiceCreditCardDTO;
import com.system.invoicegenerator.model.TransactionDTO;
import com.system.invoicegenerator.reader.InvoiceCreditCardReader;
import com.system.invoicegenerator.writer.TotalTransactionsFooterCallback;
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
            ItemStreamReader<TransactionDTO> transactionItemReader,
            ItemProcessor<InvoiceCreditCardDTO, InvoiceCreditCardDTO> loadClientDataProcessor,
            ItemWriter<InvoiceCreditCardDTO> invoiceCreditCardItemWriter,
            TotalTransactionsFooterCallback listener) {
        return stepBuilderFactory
                .get("invoiceCreditCardStep")
                .<InvoiceCreditCardDTO, InvoiceCreditCardDTO>chunk(1)
                .reader(new InvoiceCreditCardReader(transactionItemReader))
                .processor(loadClientDataProcessor)
                .writer(invoiceCreditCardItemWriter)
                .listener(listener)
                .build();
    }
}
