package com.system.invoicegenerator.model;


import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InvoiceCreditCardDTO {
    private ClientDTO client;
    private CreditCardDTO creditCard;
    private List<TransactionDTO> transactions = new ArrayList<TransactionDTO>();

    public Double getTotalValue() {
        return transactions.stream().mapToDouble(TransactionDTO::getValue)
                .reduce(0.0, Double::sum);
    }
}
