package com.system.invoicegenerator.reader;

import com.system.invoicegenerator.model.InvoiceCreditCardDTO;
import com.system.invoicegenerator.model.TransactionDTO;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

public class InvoiceCreditCardReader implements ItemStreamReader<InvoiceCreditCardDTO> {

    private ItemStreamReader<TransactionDTO> delegate;
    private TransactionDTO currentTransaction;

    public InvoiceCreditCardReader(ItemStreamReader<TransactionDTO> delegate) {
        this.delegate = delegate;
    }

    @Override
    public InvoiceCreditCardDTO read() throws Exception {
        if (currentTransaction == null)
            currentTransaction = delegate.read();

        InvoiceCreditCardDTO invoiceCreditCard = null;
        TransactionDTO transaction = currentTransaction;
        currentTransaction = null;

        if (transaction != null) {
            invoiceCreditCard = new InvoiceCreditCardDTO();
            invoiceCreditCard.setCreditCard(transaction.getCreditCard());
            invoiceCreditCard.setClient(transaction.getCreditCard().getClient());
            invoiceCreditCard.getTransactions().add(transaction);

            while (isRelatedTransaction(transaction)) {
                invoiceCreditCard.getTransactions().add(currentTransaction);
            }
        }
        return invoiceCreditCard;
    }

    private boolean isRelatedTransaction(TransactionDTO transaction) throws Exception {
        return peek() != null && transaction.getCreditCard().getCreditCardNumber() == currentTransaction.getCreditCard().getCreditCardNumber();
    }

    private TransactionDTO peek() throws Exception {
        currentTransaction = delegate.read();
        return currentTransaction;
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
