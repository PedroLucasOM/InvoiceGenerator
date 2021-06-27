package com.system.invoicegenerator.model;

import lombok.*;


@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditCard {
    private Integer creditCardNumber;
    private Integer client;
}
