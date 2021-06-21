package com.system.invoicegenerator.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Transaction {
    private int id;
    private CreditCard creditCard;
    private String description;
    private Double value;
    private Date date;
}
