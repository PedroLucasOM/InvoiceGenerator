package com.system.invoicegenerator.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditCardDTO {
    private int creditCardNumber;
    private Client client;
}
