package com.system.invoicegenerator.reader;

import com.system.invoicegenerator.model.ClientDTO;
import com.system.invoicegenerator.model.CreditCardDTO;
import com.system.invoicegenerator.model.TransactionDTO;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class TransactionItemReaderConfig {

    @Bean
    public JdbcCursorItemReader<TransactionDTO> transactionItemReader(
            @Qualifier("appDataSource") DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<TransactionDTO>()
                .name("transactionItemReader")
                .dataSource(dataSource)
                .sql("select * from transaction join credit_card using (credit_card_number) order by credit_card_number")
                .rowMapper(rowMapperTransaction())
                .build();
    }

    private RowMapper<TransactionDTO> rowMapperTransaction() {
        return new RowMapper<TransactionDTO>() {

            @Override
            public TransactionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                CreditCardDTO creditCard = new CreditCardDTO();
                creditCard.setCreditCardNumber(rs.getInt("credit_card_number"));
                ClientDTO client = new ClientDTO();
                client.setId(rs.getInt("client"));
                creditCard.setClient(client);

                TransactionDTO transaction = new TransactionDTO();
                transaction.setId(rs.getInt("id"));
                transaction.setCreditCard(creditCard);
                transaction.setDate(rs.getDate("date"));
                transaction.setValue(rs.getDouble("value"));
                transaction.setDescription(rs.getString("description"));

                return transaction;
            }

        };
    }

}
