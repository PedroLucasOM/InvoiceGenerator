package com.system.invoicegenerator.processor;

import com.system.invoicegenerator.model.ClientDTO;
import com.system.invoicegenerator.model.InvoiceCreditCardDTO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LoadClientDataProcessor implements ItemProcessor<InvoiceCreditCardDTO, InvoiceCreditCardDTO> {
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public InvoiceCreditCardDTO process(InvoiceCreditCardDTO invoiceCreditCard) throws Exception {
        String uri = String.format("https://my-json-server.typicode.com/PedroLucasOM/demo/profile/%d", invoiceCreditCard.getClient().getId());
        ResponseEntity<ClientDTO> response = restTemplate.getForEntity(uri, ClientDTO.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ValidationException("Client not found!");
        }

        invoiceCreditCard.setClient(response.getBody());
        return invoiceCreditCard;
    }

}
