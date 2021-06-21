package com.system.invoicegenerator.writer;

import com.system.invoicegenerator.model.InvoiceCreditCard;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.file.FlatFileFooterCallback;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.util.List;

public class TotalTransactionsFooterCallback implements FlatFileFooterCallback {
    private Double total = 0.0;

    @Override
    public void writeFooter(Writer writer) throws IOException {
        writer.write(String.format("\n%121s", "Total: " + NumberFormat.getCurrencyInstance().format(total)));
    }

    @BeforeWrite
    public void beforeWrite(List<InvoiceCreditCard> invoices) {
        for (InvoiceCreditCard invoiceCreditCard : invoices)
            total += invoiceCreditCard.getTotalValue();
    }

    @AfterChunk
    public void afterChunk(ChunkContext chunkContext) {
        total = 0.0;
    }
}
