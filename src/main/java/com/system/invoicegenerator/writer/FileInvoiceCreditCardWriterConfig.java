package com.system.invoicegenerator.writer;

import com.system.invoicegenerator.model.InvoiceCreditCardDTO;
import com.system.invoicegenerator.model.TransactionDTO;
import org.springframework.batch.item.file.*;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

@Configuration
public class FileInvoiceCreditCardWriterConfig {

    @Value("${spring-batch-learning.output-folder}invoice")
    private String path;

    @Bean
    public MultiResourceItemWriter<InvoiceCreditCardDTO> invoiceCreditCardItemWriter() {
        return new MultiResourceItemWriterBuilder<InvoiceCreditCardDTO>()
                .name("invoiceCreditCardItemWriter")
                .resource(new FileSystemResource(path))
                .itemCountLimitPerResource(1)
                .resourceSuffixCreator(suffixCreator())
                .delegate(invoiceCreditCardFileWriter())
                .build();
    }

    private FlatFileItemWriter<InvoiceCreditCardDTO> invoiceCreditCardFileWriter() {
        return new FlatFileItemWriterBuilder<InvoiceCreditCardDTO>()
                .name("invoiceCreditCardFileWriter")
                .resource(new FileSystemResource(path + ".txt"))
                .lineAggregator(lineAggregator())
                .headerCallback(headerCallback())
                .footerCallback(footerCallback())
                .build();
    }

    @Bean
    public FlatFileFooterCallback footerCallback() {
        return new TotalTransactionsFooterCallback();
    }

    private FlatFileHeaderCallback headerCallback() {
        return new FlatFileHeaderCallback() {

            @Override
            public void writeHeader(Writer writer) throws IOException {
                writer.append(String.format("%121s\n", "Cartão XPTO"));
                writer.append(String.format("%121s\n\n", "Rua Vergueiro, 131"));
            }
        };
    }

    private LineAggregator<InvoiceCreditCardDTO> lineAggregator() {
        return new LineAggregator<InvoiceCreditCardDTO>() {

            @Override
            public String aggregate(InvoiceCreditCardDTO invoiceCreditCard) {
                StringBuilder writer = new StringBuilder();
                writer.append(String.format("Nome: %s\n", invoiceCreditCard.getClient().getName()));
                writer.append(String.format("Endereço: %s\n\n\n", invoiceCreditCard.getClient().getAddress()));
                writer.append(String.format("Fatura completa do cartão %d\n", invoiceCreditCard.getCreditCard().getCreditCardNumber()));
                writer.append("-------------------------------------------------------------------------------------------------------------------------\n");
                writer.append("DATA DESCRICAO VALOR\n");
                writer.append("-------------------------------------------------------------------------------------------------------------------------\n");

                for (TransactionDTO transaction : invoiceCreditCard.getTransactions()) {
                    writer.append(String.format("\n[%10s] %-80s - %s",
                            new SimpleDateFormat("dd/MM/yyyy").format(transaction.getDate()),
                            transaction.getDescription(),
                            NumberFormat.getCurrencyInstance().format(transaction.getValue())));
                }
                return writer.toString();
            }

        };
    }

    private ResourceSuffixCreator suffixCreator() {
        return new ResourceSuffixCreator() {

            @Override
            public String getSuffix(int index) {
                return index + ".txt";
            }
        };
    }

}
