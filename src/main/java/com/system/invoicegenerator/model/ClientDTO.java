package com.system.invoicegenerator.model;

import lombok.*;


@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientDTO {
    private int id;
    private String name;
    private String address;
}
