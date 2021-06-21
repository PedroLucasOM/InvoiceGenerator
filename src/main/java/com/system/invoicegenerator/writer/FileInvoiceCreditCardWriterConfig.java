package com.system.invoicegenerator.writer;

import com.system.invoicegenerator.model.InvoiceCreditCard;
import com.system.invoicegenerator.model.Transaction;
import org.springframework.batch.item.file.*;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

@Configuration
public class FileInvoiceCreditCardWriterConfig {

    @Bean
    public MultiResourceItemWriter<InvoiceCreditCard> invoiceCreditCardItemWriter() {
        return new MultiResourceItemWriterBuilder<InvoiceCreditCard>()
                .name("invoiceCreditCardItemWriter")
                .resource(new FileSystemResource("files/input"))
                .itemCountLimitPerResource(1)
                .resourceSuffixCreator(suffixCreator())
                .delegate(invoiceCreditCardFileWriter())
                .build();
    }

    private FlatFileItemWriter<InvoiceCreditCard> invoiceCreditCardFileWriter() {
        return new FlatFileItemWriterBuilder<InvoiceCreditCard>()
                .name("invoiceCreditCardFileWriter")
                .resource(new FileSystemResource("files/output.txt"))
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

    private LineAggregator<InvoiceCreditCard> lineAggregator() {
        return new LineAggregator<InvoiceCreditCard>() {

            @Override
            public String aggregate(InvoiceCreditCard invoiceCreditCard) {
                StringBuilder writer = new StringBuilder();
                writer.append(String.format("Nome: %s\n", invoiceCreditCard.getClient().getName()));
                writer.append(String.format("Endereço: %s\n\n\n", invoiceCreditCard.getClient().getAddress()));
                writer.append(String.format("Fatura completa do cartão %d\n", invoiceCreditCard.getCreditCard().getCreditCardNumber()));
                writer.append("-------------------------------------------------------------------------------------------------------------------------\n");
                writer.append("DATA DESCRICAO VALOR\n");
                writer.append("-------------------------------------------------------------------------------------------------------------------------\n");

                for (Transaction transaction : invoiceCreditCard.getTransactions()) {
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
