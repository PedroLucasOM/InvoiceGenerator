package com.system.invoicegenerator.reader;

import com.system.invoicegenerator.model.InvoiceCreditCard;
import com.system.invoicegenerator.model.Transaction;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

public class InvoiceCreditCardReader implements ItemStreamReader<InvoiceCreditCard> {

    private ItemStreamReader<Transaction> delegate;
    private Transaction currentTransaction;

    @Override
    public InvoiceCreditCard read() throws Exception {
        if (currentTransaction == null)
            currentTransaction = delegate.read();

        InvoiceCreditCard invoiceCreditCard = null;
        Transaction transaction = currentTransaction;
        currentTransaction = null;

        if (transaction != null) {
            invoiceCreditCard = new InvoiceCreditCard();
            invoiceCreditCard.setCreditCard(transaction.getCreditCard());
            invoiceCreditCard.setClient(transaction.getCreditCard().getClient());
            invoiceCreditCard.getTransactions().add(transaction);

            while (isRelatedTransaction(transaction)) {
                invoiceCreditCard.getTransactions().add(currentTransaction);
            }
        }
        return invoiceCreditCard;
    }

    private boolean isRelatedTransaction(Transaction transaction) throws Exception {
        return peek() != null && transaction.getCreditCard().getCreditCardNumber() == currentTransaction.getCreditCard().getCreditCardNumber();
    }

    private Transaction peek() throws Exception {
        currentTransaction = delegate.read();
        return currentTransaction;
    }

    public InvoiceCreditCardReader(ItemStreamReader<Transaction> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        delegate.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        delegate.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        delegate.close();
    }

}
