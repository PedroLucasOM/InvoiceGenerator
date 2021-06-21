package com.system.invoicegenerator.model;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class InvoiceCreditCard {
    private Client client;
    private CreditCard creditCard;
    private List<Transaction> transactions = new ArrayList<Transaction>();

    public Double getTotalValue() {
        return transactions.stream().mapToDouble(Transaction::getValue)
                .reduce(0.0, Double::sum);
    }
}
