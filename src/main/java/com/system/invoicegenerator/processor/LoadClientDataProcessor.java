package com.system.invoicegenerator.processor;

import com.system.invoicegenerator.model.Client;
import com.system.invoicegenerator.model.InvoiceCreditCard;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LoadClientDataProcessor implements ItemProcessor<InvoiceCreditCard, InvoiceCreditCard> {
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public InvoiceCreditCard process(InvoiceCreditCard invoiceCreditCard) throws Exception {
        String uri = String.format("https://my-json-server.typicode.com/PedroLucasOM/demo/profile/%d", invoiceCreditCard.getClient().getId());
        ResponseEntity<Client> response = restTemplate.getForEntity(uri, Client.class);

        if (response.getStatusCode() != HttpStatus.OK)
            throw new ValidationException("Client not found!");

        invoiceCreditCard.setClient(response.getBody());
        return invoiceCreditCard;
    }

}
