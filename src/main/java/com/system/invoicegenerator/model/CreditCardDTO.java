package com.system.invoicegenerator.model;

import lombok.*;


@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditCardDTO {
    private int creditCardNumber;
    private ClientDTO client;
}
