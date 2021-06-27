package com.system.invoicegenerator.model;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionDTO {
    private int id;
    private CreditCardDTO creditCard;
    private String description;
    private Double value;
    private Date date;
}
