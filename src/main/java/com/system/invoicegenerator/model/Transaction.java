package com.system.invoicegenerator.model;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction {
    private Integer id;
    private Integer creditCardNumber;
    private String description;
    private Double value;
    private Date date;
}
